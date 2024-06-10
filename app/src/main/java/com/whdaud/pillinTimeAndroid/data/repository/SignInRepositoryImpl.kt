package com.whdaud.pillinTimeAndroid.data.repository

import com.whdaud.pillinTimeAndroid.data.local.LocalUserDataSource
import com.whdaud.pillinTimeAndroid.data.remote.SignInService
import com.whdaud.pillinTimeAndroid.data.remote.dto.TokenDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.SignInSmsAuthRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.SignInSmsAuthResponse
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.whdaud.pillinTimeAndroid.domain.repository.SignInRepository
import com.whdaud.pillinTimeAndroid.domain.repository.TokenRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val signInService: SignInService,
    private val tokenRepository: TokenRepository,
    private val localUserDataSource: LocalUserDataSource
) : SignInRepository {
    override suspend fun signIn(signInRequest: SignInRequest): Result<BaseResponse<TokenDTO>> {
        return try {
            val response = signInService.postSignIn(signInRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut(): Result<BaseResponse<Any>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = signInService.postSignOut("Bearer $accessToken")
            if(response.status == 200) {
                localUserDataSource.deleteAccessToken()
                localUserDataSource.saveUserName("")
                Result.success(response)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postSmsAuth(signInSmsAuthRequest: SignInSmsAuthRequest): Result<BaseResponse<SignInSmsAuthResponse>> {
        return try {
            val response = signInService.postSmsAuth(signInSmsAuthRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}