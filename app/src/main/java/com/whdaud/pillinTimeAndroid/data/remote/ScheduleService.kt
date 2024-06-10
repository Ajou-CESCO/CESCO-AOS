package com.whdaud.pillinTimeAndroid.data.remote

import com.whdaud.pillinTimeAndroid.data.remote.dto.ScheduleDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.ScheduleRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface ScheduleService {
    @GET("/api/dose/plan")
    suspend fun getDoseSchedule(
        @Header("Authorization") accessToken: String,
        @Query("memberId") memberId: Int
    ) : BaseResponse<List<ScheduleDTO>>
    @POST("/api/dose/plan")
    suspend fun postDoseSchedule(
        @Header("Authorization") accessToken: String,
        @Body scheduleRequest: ScheduleRequest
    ): BaseResponse<Any>
    @DELETE("/api/dose/plan")
    suspend fun deleteDoseSchedule(
        @Header("Authorization") accessToken: String,
        @Query("memberId") memberId: Int,
        @Query("groupId") groupId: Int
    ): BaseResponse<Any>
    @GET("/api/dose/log")
    suspend fun getScheduleLog(
        @Header("Authorization") accessToken: String,
        @Query("memberId") memberId: Int,
        @Query("date") date: String
    ) : BaseResponse<ScheduleLogDTO>

    @PATCH("/api/dose/plan")
    suspend fun patchDoseSchedule(
        @Header("Authorization") accessToken: String,
        @Body scheduleDTO: ScheduleDTO
    ): BaseResponse<Any>
}