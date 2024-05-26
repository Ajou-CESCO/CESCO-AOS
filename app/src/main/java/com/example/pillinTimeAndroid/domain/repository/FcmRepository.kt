package com.example.pillinTimeAndroid.domain.repository

import com.example.pillinTimeAndroid.data.remote.dto.FCMTokenDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.FcmPushNotificationRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse

interface FcmRepository {
    suspend fun postFcmToken(fcmTokenDTO: FCMTokenDTO): Result<BaseResponse<Any>>
    suspend fun postFcmPushNotification(fcmPushNotificationRequest: FcmPushNotificationRequest): Result<BaseResponse<Any>>
}