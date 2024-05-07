package com.example.pillinTimeAndroid.data.repository

import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.data.remote.UserService
import com.example.pillinTimeAndroid.data.remote.dto.UserDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.example.pillinTimeAndroid.domain.entity.User
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource,
    private val userService: UserService
) : UserRepository {
    override suspend fun saveUserSession(user: User) {
        user.accessToken?.let { localUserDataSource.saveAccessToken(it) }
        user.name?.let { localUserDataSource.saveUserName(it) }
        user.phone?.let { localUserDataSource.saveUserPhone(it) }
        user.ssn?.let { localUserDataSource.saveUserSsn(it) }
        user.phone?.let { localUserDataSource.saveUserPhone(it) }
        user.userType?.let { localUserDataSource.saveUserType(it) }
    }

    override fun readUserSession(): Flow<User?> {
        return localUserDataSource.readUserSession()
    }

    override suspend fun getUserInfo(accessToken: String): Result<BaseResponse<UserDTO>> {
        return try {
            val response = userService.getUserInfo(accessToken)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun patchUserInfo(
        accessToken: String,
        signInRequest: SignInRequest
    ): Result<BaseResponse<UserDTO>> {
        return try {
            val response = userService.patchUserInfo(accessToken, signInRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}