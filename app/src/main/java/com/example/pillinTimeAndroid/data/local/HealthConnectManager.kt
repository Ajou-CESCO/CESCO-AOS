package com.example.pillinTimeAndroid.data.local

import android.content.Context
import android.os.Build
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.HealthConnectClient.Companion.SDK_AVAILABLE
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.Instant
import javax.inject.Inject

const val MIN_SUPPORTED_SDK = Build.VERSION_CODES.O_MR1

class HealthConnectManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(context) }

    var availability = mutableStateOf(HealthConnectAvailability.NOT_INSTALLED)
        private set

    init {
        checkAvailability()
    }

    fun checkAvailability() {
        availability.value = when {
            HealthConnectClient.getSdkStatus(context) == SDK_AVAILABLE -> HealthConnectAvailability.INSTALLED
            isSupported() -> HealthConnectAvailability.NOT_INSTALLED
            else -> HealthConnectAvailability.NOT_SUPPORTED
        }
    }

    suspend fun hasAllPermissions(permissions: Set<String>): Boolean {
        return healthConnectClient.permissionController.getGrantedPermissions()
            .containsAll(permissions)
    }

    fun requestPermissionsActivityContract(): ActivityResultContract<Set<String>, Set<String>> {
        return PermissionController.createRequestPermissionResultContract()
    }

    suspend fun readSleepSessions(start: Instant, end: Instant): List<SleepSessionRecord> {
        val request = ReadRecordsRequest(
            recordType = SleepSessionRecord::class,
            timeRangeFilter = TimeRangeFilter.Companion.between(start, end)
        )
        return healthConnectClient.readRecords(request).records
    }
    suspend fun aggregateSleepSessions(
        start: Instant,
        end: Instant
    ): Duration? {
        return try {
            val response =
                healthConnectClient.aggregate(
                    AggregateRequest(
                        setOf(SleepSessionRecord.SLEEP_DURATION_TOTAL),
                        timeRangeFilter = TimeRangeFilter.between(start, end)
                    )
                )
            response[SleepSessionRecord.SLEEP_DURATION_TOTAL]
        } catch (e: Exception) {
            e.printStackTrace()
            Duration.ZERO
        }
    }
    suspend fun readSteps(start: Instant, end: Instant): List<StepsRecord> {
        val request = ReadRecordsRequest(
            recordType = StepsRecord::class,
            timeRangeFilter = TimeRangeFilter.Companion.between(start, end)
        )
        return healthConnectClient.readRecords(request).records
    }

    suspend fun readHeartRates(start: Instant, end: Instant): List<HeartRateRecord> {
        val request = ReadRecordsRequest(
            recordType = HeartRateRecord::class,
            timeRangeFilter = TimeRangeFilter.Companion.between(start, end)
        )
        return healthConnectClient.readRecords(request).records
    }
    suspend fun aggregateHeartRate(
        start: Instant,
        end: Instant
    ): Long? {
        return try {
            val response =
                healthConnectClient.aggregate(
                    AggregateRequest(
                        setOf(HeartRateRecord.BPM_AVG),
                        timeRangeFilter = TimeRangeFilter.between(start, end)
                    )
                )
            response[HeartRateRecord.BPM_AVG]
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
    suspend fun readTotalCaloriesBurned(start: Instant, end: Instant): List<TotalCaloriesBurnedRecord> {
        val request = ReadRecordsRequest(
            recordType = TotalCaloriesBurnedRecord::class,
            timeRangeFilter = TimeRangeFilter.Companion.between(start, end)
        )
        return healthConnectClient.readRecords(request).records
    }

    // minSDK will be changed below 34 later on
    private fun isSupported() = Build.VERSION.SDK_INT >= MIN_SUPPORTED_SDK

}

// for now, only check installed or not installed.
enum class HealthConnectAvailability {
    INSTALLED,
    NOT_INSTALLED,
    NOT_SUPPORTED
}