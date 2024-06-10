package com.whdaud.pillinTimeAndroid.data.repository

import com.whdaud.pillinTimeAndroid.data.remote.UserService
import com.whdaud.pillinTimeAndroid.data.remote.dto.TokenDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.SignUpRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.whdaud.pillinTimeAndroid.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val userService: UserService
) : SignUpRepository {
    override suspend fun signUp(signUpRequest: SignUpRequest): Result<BaseResponse<TokenDTO>> {
        return try {
            val response = userService.postUser(signUpRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}