package com.example.pillinTimeAndroid.domain.repository

import com.example.pillinTimeAndroid.data.remote.dto.TokenDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.SignUpRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse

interface SignUpRepository {
    suspend fun signUp(signUpRequest: SignUpRequest): Result<BaseResponse<TokenDTO>>
}