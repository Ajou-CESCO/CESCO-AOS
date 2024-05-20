package com.example.pillinTimeAndroid.domain.repository

import com.example.pillinTimeAndroid.data.remote.dto.TokenDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInSmsAuthRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.SignInSmsAuthResponse
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse

interface SignInRepository {
    suspend fun signIn(signInRequest: SignInRequest): Result<BaseResponse<TokenDTO>>
    suspend fun postSmsAuth(signInSmsAuthRequest: SignInSmsAuthRequest): Result<BaseResponse<SignInSmsAuthResponse>>
}