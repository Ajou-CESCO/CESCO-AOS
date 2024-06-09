package com.whdaud.pillinTimeAndroid.presentation.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whdaud.pillinTimeAndroid.data.local.HealthConnectManager
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.HealthDataRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.HealthStatDTO
import com.whdaud.pillinTimeAndroid.domain.repository.UserRepository
import com.whdaud.pillinTimeAndroid.presentation.home.components.roundToNearestHour
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val healthConnectManager: HealthConnectManager,
    private val userRepository: UserRepository
) : ViewModel() {

    val permissions = setOf(
        HealthPermission.getReadPermission(SleepSessionRecord::class),
        HealthPermission.getReadPermission(StepsRecord::class),
        HealthPermission.getReadPermission(HeartRateRecord::class),
        HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class)
    )
    var permissionsGranted = mutableStateOf(false)
        private set
    val permissionsLauncher = healthConnectManager.requestPermissionsActivityContract()

    private val _sleepData = MutableStateFlow<Duration?>(Duration.ZERO)
    val sleepData: StateFlow<Duration?> = _sleepData.asStateFlow()

    private val _stepsData = MutableStateFlow<Long>(0)
    val stepsData: StateFlow<Long> = _stepsData.asStateFlow()

    private val _heartRateData = MutableStateFlow<Long?>(0)
    val heartRateData: StateFlow<Long?> = _heartRateData.asStateFlow()

    private val _totalCaloriesData = MutableStateFlow<Long>(0)
    val totalCaloriesData: StateFlow<Long> = _totalCaloriesData.asStateFlow()

    private val _remoteHealthData = MutableStateFlow<HealthStatDTO?>(null)
    val remoteHealthData: StateFlow<HealthStatDTO?> = _remoteHealthData.asStateFlow()

    fun fetchLocalHealthData() {
        viewModelScope.launch {
            permissionsGranted.value = healthConnectManager.hasAllPermissions(permissions)
            if (permissionsGranted.value) {
                val startTime =
                    LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
                val endTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()
                val steps = healthConnectManager.readSteps(startTime, endTime)
                _stepsData.value = steps.sumOf { it.count }

                _heartRateData.value = healthConnectManager.aggregateHeartRate(startTime, endTime)

                _sleepData.value = healthConnectManager.aggregateSleepSessions(startTime, endTime)

                val totalCalories = healthConnectManager.readTotalCaloriesBurned(startTime, endTime)
                _totalCaloriesData.value = totalCalories.sumOf { it.energy.inKilocalories }.toLong()

                Log.e(
                    "Health Data",
                    "${_sleepData.value}${_stepsData.value}${_heartRateData.value}${_totalCaloriesData.value}"
                )
            }
        }
    }

    fun postLocalHealthData() {
        viewModelScope.launch {
            val request = HealthDataRequest(
                steps = _stepsData.value.toInt(),
                sleepTime = if(_sleepData.value != null) _sleepData.value!!.roundToNearestHour() else 0,
                calorie =  _totalCaloriesData.value.toInt(),
                heartRate = if(_heartRateData.value != null) _heartRateData.value!!.toInt() else 0
            )
            val result = userRepository.postHealthData(request)
            result.onSuccess {response ->
                Log.e("Post Health Data", "succeed to post remote health data: ${response.result}")
            }.onFailure {
                Log.e("Post Health Data", "failed to post remote health data: ${it.message}")
            }
        }
    }

    fun getRemoteHealthData(memberId: Int, isManager: Boolean) {
        viewModelScope.launch {
            val result = userRepository.getHealthData(memberId)
            result.onSuccess { response ->
                _remoteHealthData.value = response.result
                // 피보호자의 건강 정보
                if (!isManager) {
                    fetchLocalHealthData()
                    postLocalHealthData()
                }
                Log.e("Health Data", "succeed to get remote health data: ${response.result}")
                Log.e("Health Dataaaa", "succeed to get remote health data: ${_remoteHealthData.value}")
            }.onFailure {
                Log.e("Health Data", "failed to get remote health data: ${it.message}")
            }
        }
    }
}