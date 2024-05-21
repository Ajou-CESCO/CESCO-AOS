package com.example.pillinTimeAndroid.data.repository

import com.example.pillinTimeAndroid.data.remote.CabinetService
import com.example.pillinTimeAndroid.data.remote.UserService
import com.example.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.example.pillinTimeAndroid.data.remote.dto.UserDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.CabinetRequest
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.example.pillinTimeAndroid.domain.repository.TokenRepository
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import java.util.Objects
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val userService: UserService,
    private val cabinetService: CabinetService
) : UserRepository {

    override suspend fun initClient(): Result<BaseResponse<UserDTO<List<RelationDTO>>>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = userService.initClient("Bearer $accessToken")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserInfo(): Result<BaseResponse<UserDTO<Any>>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = userService.getUserInfo("Bearer $accessToken")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun patchUserInfo(
        accessToken: String,
        signInRequest: SignInRequest
    ): Result<BaseResponse<UserDTO<Any>>> {
        return try {
            val response = userService.patchUserInfo(accessToken, signInRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUserInfo(): Result<BaseResponse<Any>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = userService.deleteUserInfo("Bearer $accessToken")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postRegisterCabinet(
        accessToken: String,
        cabinetRequest: CabinetRequest
    ): Result<BaseResponse<Objects>> {
        return try {
            val response = cabinetService.postRegisterCabinet(accessToken, cabinetRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}