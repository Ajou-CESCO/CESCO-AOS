package com.example.pillinTimeAndroid.data.repository

import com.example.pillinTimeAndroid.data.remote.MedicineService
import com.example.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.example.pillinTimeAndroid.domain.repository.MedicineRepository
import javax.inject.Inject

class MedicineRepositoryImpl @Inject constructor(
    private val medicineService: MedicineService
) : MedicineRepository {
    override suspend fun getMedicineInfo(accessToken: String, medicineName: String): Result<BaseResponse<List<MedicineDTO>>> {
        return try {
            val response = medicineService.getMedicineInfo(accessToken, medicineName)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
