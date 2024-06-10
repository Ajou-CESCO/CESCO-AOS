package com.whdaud.pillinTimeAndroid.data.remote

import com.whdaud.pillinTimeAndroid.data.remote.dto.TokenDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.SignInSmsAuthRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.SignInSmsAuthResponse
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface SignInService {
    @POST("/api/auth")
    suspend fun postSignIn(
        @Body signInRequest: SignInRequest
    ): BaseResponse<TokenDTO>

    @GET("/api/auth/logout")
    suspend fun postSignOut(
        @Header("Authorization") accessToken: String
    ): BaseResponse<Any>

    @POST("/api/auth/sms")
    suspend fun postSmsAuth(
        @Body signInSmsAuthRequest: SignInSmsAuthRequest
    ): BaseResponse<SignInSmsAuthResponse>
}