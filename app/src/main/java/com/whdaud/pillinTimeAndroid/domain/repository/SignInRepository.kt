package com.whdaud.pillinTimeAndroid.domain.repository

import com.whdaud.pillinTimeAndroid.data.remote.dto.TokenDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.SignInSmsAuthRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.SignInSmsAuthResponse
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse

interface SignInRepository {
    suspend fun signIn(signInRequest: SignInRequest): Result<BaseResponse<TokenDTO>>
    suspend fun signOut(): Result<BaseResponse<Any>>
    suspend fun postSmsAuth(signInSmsAuthRequest: SignInSmsAuthRequest): Result<BaseResponse<SignInSmsAuthResponse>>
}