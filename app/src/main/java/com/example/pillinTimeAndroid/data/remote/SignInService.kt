package com.example.pillinTimeAndroid.data.remote

import com.example.pillinTimeAndroid.data.remote.dto.TokenDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInSmsAuthRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.SignInSmsAuthResponse
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SignInService {
    @POST("/api/auth")
    suspend fun postSignIn(
        @Body signInRequest: SignInRequest
    ): BaseResponse<TokenDTO>

    @POST("/api/auth/sms")
    suspend fun postSmsAuth(
        @Header("Authorization") accessToken: String,
        @Body signInSmsAuthRequest: SignInSmsAuthRequest
    ): BaseResponse<SignInSmsAuthResponse>
}