package com.whdaud.pillinTimeAndroid.data.remote

import com.whdaud.pillinTimeAndroid.data.remote.dto.FCMTokenDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.FcmPushNotificationRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface FcmService {
    @POST("/api/fcm/token")
    suspend fun postFcmToken(
        @Header("Authorization") accessToken: String,
        @Body fcmTokenDTO: FCMTokenDTO
    ): BaseResponse<Any>

    @POST("/api/fcm/push")
    suspend fun postFcmPushNotification(
        @Header("Authorization") accessToken: String,
        @Body fcmPushNotificationRequest: FcmPushNotificationRequest
    ): BaseResponse<Any>
}