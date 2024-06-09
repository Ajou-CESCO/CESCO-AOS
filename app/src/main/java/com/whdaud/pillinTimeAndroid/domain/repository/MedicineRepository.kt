package com.whdaud.pillinTimeAndroid.domain.repository

import com.whdaud.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.ScheduleDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.ScheduleRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse

interface MedicineRepository {
    suspend fun getMedicineInfo(memberId: Int, medicineName: String): Result<BaseResponse<List<MedicineDTO>>>
    suspend fun getMedicineInfoV2(medicineId: Int): Result<BaseResponse<MedicineDTO>>
    suspend fun postDoseSchedule(scheduleRequest: ScheduleRequest): Result<BaseResponse<Any>>
    suspend fun getDoseSchedule(memberId: Int): Result<BaseResponse<List<ScheduleDTO>>>
    suspend fun deleteDoseSchedule(memberId: Int, medicineId: String, cabinetIndex: Int): Result<BaseResponse<Any>>
    suspend fun getDoseLog(memberId: Int): Result<BaseResponse<ScheduleLogDTO>>
}