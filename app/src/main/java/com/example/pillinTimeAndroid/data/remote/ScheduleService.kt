package com.example.pillinTimeAndroid.data.remote

import com.example.pillinTimeAndroid.data.remote.dto.ScheduleDTO
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.ScheduleRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ScheduleService {
    @GET("/api/dose/plan")
    suspend fun getDoseSchedule(
        @Header("Authorization") accessToken: String,
        @Query("memberId") memberId: Int,
        @Query("medicineId") medicineId: String,
        @Query("cabinetIndex") cabinetIndex: Int
    ) : BaseResponse<List<ScheduleDTO>>

    @POST("/api/dose/plan")
    suspend fun postDoseSchedule(
        @Header("Authorization") accessToken: String,
        @Body scheduleRequest: ScheduleRequest
    ): BaseResponse<Any>

    @GET("/api/dose/log")
    suspend fun getScheduleLog(
        @Header("Authorization") accessToken: String,
        @Query("memberId") memberId: Int
    ) : BaseResponse<List<ScheduleLogDTO>>
}