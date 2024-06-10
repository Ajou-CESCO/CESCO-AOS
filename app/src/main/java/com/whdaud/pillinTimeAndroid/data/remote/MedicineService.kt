package com.whdaud.pillinTimeAndroid.data.remote

import com.whdaud.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MedicineService {
    @GET("/api/medicine")
    suspend fun getMedicineInfo(
        @Header("Authorization") accessToken: String,
        @Query("memberId") memberId: Int,
        @Query("name") medicineName: String
    ): BaseResponse<List<MedicineDTO>>

    @GET("/api/medicine/{medicineId}")
    suspend fun getMedicineInfoV2(
        @Header("Authorization") accessToken: String,
        @Path("medicineId") medicineId: Int
    ): BaseResponse<MedicineDTO>
}