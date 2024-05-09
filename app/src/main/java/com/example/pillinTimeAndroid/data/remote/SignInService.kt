package com.example.pillinTimeAndroid.data.remote

import com.example.pillinTimeAndroid.data.remote.dto.TokenDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST("/api/auth")
    suspend fun postSignIn(
        @Body signInRequest: SignInRequest
    ): BaseResponse<TokenDTO>
}