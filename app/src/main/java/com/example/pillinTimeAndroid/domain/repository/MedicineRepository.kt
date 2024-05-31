package com.example.pillinTimeAndroid.domain.repository

import com.example.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleDTO
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.ScheduleRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse

interface MedicineRepository {
    suspend fun getMedicineInfo(medicineName: String): Result<BaseResponse<List<MedicineDTO>>>
    suspend fun postDoseSchedule(scheduleRequest: ScheduleRequest): Result<BaseResponse<Any>>
    suspend fun getDoseSchedule(memberId: Int, medicineId: String, cabinetIndex: Int): Result<BaseResponse<List<ScheduleDTO>>>
    suspend fun getDoseLog(memberId: Int): Result<BaseResponse<List<ScheduleLogDTO>>>

}