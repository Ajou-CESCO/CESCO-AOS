package com.whdaud.pillinTimeAndroid.data.repository

import com.whdaud.pillinTimeAndroid.data.remote.MedicineService
import com.whdaud.pillinTimeAndroid.data.remote.ScheduleService
import com.whdaud.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.ScheduleDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.ScheduleRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.whdaud.pillinTimeAndroid.domain.repository.MedicineRepository
import com.whdaud.pillinTimeAndroid.domain.repository.TokenRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class MedicineRepositoryImpl @Inject constructor(
    private val medicineService: MedicineService,
    private val scheduleService: ScheduleService,
    private val tokenRepository: TokenRepository
) : MedicineRepository {
    override suspend fun getMedicineInfo(
        memberId: Int,
        medicineName: String
    ): Result<BaseResponse<List<MedicineDTO>>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = medicineService.getMedicineInfo("Bearer $accessToken", memberId, medicineName)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMedicineInfoV2(medicineId: Int): Result<BaseResponse<MedicineDTO>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = medicineService.getMedicineInfoV2("Bearer $accessToken", medicineId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postDoseSchedule(scheduleRequest: ScheduleRequest): Result<BaseResponse<Any>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = scheduleService.postDoseSchedule("Bearer $accessToken", scheduleRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDoseSchedule(memberId: Int): Result<BaseResponse<List<ScheduleDTO>>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = scheduleService.getDoseSchedule("Bearer $accessToken", memberId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteDoseSchedule(
        memberId: Int,
        medicineId: String,
        cabinetIndex: Int
    ): Result<BaseResponse<Any>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = scheduleService.deleteDoseSchedule("Bearer $accessToken", memberId, medicineId, cabinetIndex)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDoseLog(memberId: Int): Result<BaseResponse<ScheduleLogDTO>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = scheduleService.getScheduleLog("Bearer $accessToken", memberId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
