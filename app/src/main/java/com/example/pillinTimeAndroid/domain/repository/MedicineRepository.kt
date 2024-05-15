package com.example.pillinTimeAndroid.domain.repository

import com.example.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.ScheduleRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse

interface MedicineRepository {
    suspend fun getMedicineInfo(accessToken: String, medicineName: String): Result<BaseResponse<List<MedicineDTO>>>
    suspend fun postDoseSchedule(accessToken: String, scheduleRequest: ScheduleRequest): Result<BaseResponse<Any>>
    suspend fun getDoseLog(accessToken: String, memberId: Int): Result<BaseResponse<List<ScheduleLogDTO>>>
}