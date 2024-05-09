package com.example.pillinTimeAndroid.data.repository

import com.example.pillinTimeAndroid.data.remote.UserService
import com.example.pillinTimeAndroid.data.remote.dto.TokenDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.SignUpRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.example.pillinTimeAndroid.domain.repository.SignUpRepository
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