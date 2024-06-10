package com.whdaud.pillinTimeAndroid.domain.repository

import com.whdaud.pillinTimeAndroid.data.remote.dto.TokenDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.SignUpRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse

interface SignUpRepository {
    suspend fun signUp(signUpRequest: SignUpRequest): Result<BaseResponse<TokenDTO>>
}