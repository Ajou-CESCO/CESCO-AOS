package com.example.pillinTimeAndroid.data.repository

import com.example.pillinTimeAndroid.data.remote.FcmService
import com.example.pillinTimeAndroid.data.remote.dto.FCMTokenDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.FcmPushNotificationRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.example.pillinTimeAndroid.domain.repository.FcmRepository
import com.example.pillinTimeAndroid.domain.repository.TokenRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class FcmRepositoryImpl @Inject constructor(
    private val fcmService: FcmService,
    private val tokenRepository: TokenRepository
) : FcmRepository {
    override suspend fun postFcmToken(fcmTokenDTO: FCMTokenDTO): Result<BaseResponse<Any>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = fcmService.postFcmToken("Bearer $accessToken", fcmTokenDTO)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postFcmPushNotification(fcmPushNotificationRequest: FcmPushNotificationRequest): Result<BaseResponse<Any>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = fcmService.postFcmPushNotification("Bearer $accessToken", fcmPushNotificationRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }    }
}