package com.example.pillinTimeAndroid.data.repository

import com.example.pillinTimeAndroid.data.remote.SignInService
import com.example.pillinTimeAndroid.data.remote.dto.TokenDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInSmsAuthRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.SignInSmsAuthResponse
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.example.pillinTimeAndroid.domain.repository.SignInRepository
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val signInService: SignInService,
) : SignInRepository {
    override suspend fun signIn(signInRequest: SignInRequest): Result<BaseResponse<TokenDTO>> {
        return try {
            val response = signInService.postSignIn(signInRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postSmsAuth(signInSmsAuthRequest: SignInSmsAuthRequest): Result<BaseResponse<SignInSmsAuthResponse>> {
        return try {
            val response = signInService.postSmsAuth(signInSmsAuthRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}