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
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.RelationReqResponse
import com.whdaud.pillinTimeAndroid.domain.repository.RelationRepository
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
    private val userRepository: UserRepository,
    private val relationRepository: RelationRepository
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

    private val _managerRequest = MutableStateFlow<List<RelationReqResponse>>(emptyList())
    val managerRequest: StateFlow<List<RelationReqResponse>> = _managerRequest.asStateFlow()

    init {
        getManagerRequest()
    }
    fun postLocalHealthData(memberId: Int) {
        viewModelScope.launch {
            permissionsGranted.value = healthConnectManager.hasAllPermissions(permissions)
            if (permissionsGranted.value) {
                val startTime = LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
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
            getRemoteHealthData(memberId)
        }
    }

    fun getRemoteHealthData(memberId: Int) {
        viewModelScope.launch {
            val result = userRepository.getHealthData(memberId)
            result.onSuccess { response ->
                _remoteHealthData.value = response.result
                Log.e("Health Dataaaa", "succeed to get remote health data: ${_remoteHealthData.value}")
            }.onFailure {
                Log.e("Health Data", "failed to get remote health data: ${it.message}")
            }
        }
    }

    fun getManagerRequest() {
        viewModelScope.launch {
            val response = relationRepository.getRelationRequest()
            response.onSuccess { requestResponse ->
                _managerRequest.value = requestResponse.result
            }.onFailure { requestResponse ->
                Log.e("fetch Manager Request", "Failed to fetch requests: ${requestResponse.message}")
            }
        }
    }

    fun acceptManagerRequest(requestId: Int, managerName: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val response = relationRepository.postRelation(requestId)
            response.onSuccess {
                if(it.status == 200) {
                    onResult("${managerName}님과 보호관계를 맺었습니다")
                } else {
                    onResult("네트워크 에러 발생 다시 시도해주세요")
                }
                Log.e("post relation", "succeed to make relation: ${it.message}")
            }.onFailure {
                if(it.message?.contains("403") == true) {
                    onResult("${managerName}님이 프리미엄 회원이 아닙니다")
                } else {
                    onResult("알 수 없는 오류 발생 다시 시도해주세요")
                }
                Log.e("post relation", "Failed to make relation: ${it.message}")
            }
        }
    }
}