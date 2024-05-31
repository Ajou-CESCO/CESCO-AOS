package com.example.pillinTimeAndroid.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillinTimeAndroid.data.local.LocalHealthConnectManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val healthConnectManager: LocalHealthConnectManager
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

    private val selectedIndex = MutableStateFlow(0)
    private val _sleepData = MutableStateFlow<List<SleepSessionRecord>>(emptyList())
    val sleepData: StateFlow<List<SleepSessionRecord>> = _sleepData.asStateFlow()

    private val _stepsData = MutableStateFlow<Long>(0)
    val stepsData: StateFlow<Long> = _stepsData.asStateFlow()

    private val _heartRateData = MutableStateFlow<List<HeartRateRecord>>(emptyList())
    val heartRateData: StateFlow<List<HeartRateRecord>> = _heartRateData.asStateFlow()

    private val _totalCaloriesData = MutableStateFlow<Long>(0)
    val totalCaloriesData: StateFlow<Long> = _totalCaloriesData.asStateFlow()

    fun fetchHealthData() {
        viewModelScope.launch {
            permissionsGranted.value = healthConnectManager.hasAllPermissions(permissions)
            if (permissionsGranted.value) {
                val startTime = LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
                val endTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()

                val sleepSessions = healthConnectManager.readSleepSessions(startTime, endTime)
                _sleepData.value = sleepSessions

                val steps = healthConnectManager.readSteps(startTime, endTime)
                _stepsData.value = steps.sumOf { it.count }

                val heartRates = healthConnectManager.readHeartRates(startTime, endTime)
                _heartRateData.value = heartRates

                val totalCalories = healthConnectManager.readTotalCaloriesBurned(startTime, endTime)
                _totalCaloriesData.value = totalCalories.sumOf { it.energy.inKilocalories }.toLong()
            }
        }
    }
}