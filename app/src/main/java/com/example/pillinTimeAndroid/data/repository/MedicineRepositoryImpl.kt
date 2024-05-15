package com.example.pillinTimeAndroid.data.repository

import com.example.pillinTimeAndroid.data.remote.MedicineService
import com.example.pillinTimeAndroid.data.remote.ScheduleService
import com.example.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.ScheduleRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.example.pillinTimeAndroid.domain.repository.MedicineRepository
import javax.inject.Inject

class MedicineRepositoryImpl @Inject constructor(
    private val medicineService: MedicineService,
    private val scheduleService: ScheduleService
) : MedicineRepository {
    override suspend fun getMedicineInfo(
        accessToken: String,
        medicineName: String
    ): Result<BaseResponse<List<MedicineDTO>>> {
        return try {
            val response = medicineService.getMedicineInfo(accessToken, medicineName)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postDoseSchedule(
        accessToken: String,
        scheduleRequest: ScheduleRequest
    ): Result<BaseResponse<Any>> {
        return try {
            val response = scheduleService.postDoseSchedule(accessToken, scheduleRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDoseLog(
        accessToken: String,
        memberId: Int
    ): Result<BaseResponse<List<ScheduleLogDTO>>> {
        return try {
            val response = scheduleService.getScheduleLog(accessToken, memberId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
