package com.example.pillinTimeAndroid.domain.repository

import com.example.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse

interface MedicineRepository {
    suspend fun getMedicineInfo(accessToken: String, medicineName: String): Result<BaseResponse<List<MedicineDTO>>>
}