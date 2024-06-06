package com.example.pillinTimeAndroid.data.remote

import com.example.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MedicineService {
    @GET("/api/medicine")
    suspend fun getMedicineInfo(
        @Header("Authorization") accessToken: String,
        @Query("memberId") memberId: Int,
        @Query("name") medicineName: String
    ): BaseResponse<List<MedicineDTO>>
}