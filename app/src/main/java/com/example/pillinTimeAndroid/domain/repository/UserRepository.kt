package com.example.pillinTimeAndroid.domain.repository

import com.example.pillinTimeAndroid.data.remote.dto.UserDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.example.pillinTimeAndroid.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUserSession(user: User)
    fun readUserSession(): Flow<User?>
    // 이게 나중에는 필요 없을 듯. 그냥 accesstoken 불러들이는게 더 단순할 것 같음
    suspend fun getUserInfo(accessToken: String): Result<BaseResponse<UserDTO>>
    suspend fun patchUserInfo(accessToken: String, signInRequest: SignInRequest): Result<BaseResponse<UserDTO>>
}