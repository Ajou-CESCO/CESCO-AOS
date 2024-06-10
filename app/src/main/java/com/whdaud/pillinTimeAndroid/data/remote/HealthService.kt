package com.whdaud.pillinTimeAndroid.data.remote

import com.whdaud.pillinTimeAndroid.data.remote.dto.request.HealthDataRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.HealthStatDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface HealthService {
    @GET("/api/health/{memberId}")
    suspend fun getHealthData(
        @Header("Authorization") accessToken: String,
        @Path("memberId") memberId: Int
    ): BaseResponse<HealthStatDTO>

    @POST("/api/health")
    suspend fun postHealthData(
        @Header("Authorization") accessToken: String,
        @Body healthDataRequest: HealthDataRequest
    ): BaseResponse<Any>
}