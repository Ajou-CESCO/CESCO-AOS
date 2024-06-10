package com.whdaud.pillinTimeAndroid.data.repository

import com.whdaud.pillinTimeAndroid.data.remote.CabinetService
import com.whdaud.pillinTimeAndroid.data.remote.HealthService
import com.whdaud.pillinTimeAndroid.data.remote.UserService
import com.whdaud.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.UserDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.CabinetRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.HealthDataRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.HealthStatDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.whdaud.pillinTimeAndroid.domain.repository.TokenRepository
import com.whdaud.pillinTimeAndroid.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val userService: UserService,
    private val cabinetService: CabinetService,
    private val healthService: HealthService
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

    override suspend fun postRegisterCabinet(cabinetRequest: CabinetRequest): Result<BaseResponse<Any>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = cabinetService.postRegisterCabinet("Bearer $accessToken", cabinetRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteCabinet(cabinetId: Int): Result<BaseResponse<Any>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = cabinetService.deleteCabinet("Bearer $accessToken", cabinetId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getHealthData(memberId: Int): Result<BaseResponse<HealthStatDTO>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = healthService.getHealthData("Bearer $accessToken", memberId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postHealthData(healthDataRequest: HealthDataRequest): Result<BaseResponse<Any>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = healthService.postHealthData("Bearer $accessToken", healthDataRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}