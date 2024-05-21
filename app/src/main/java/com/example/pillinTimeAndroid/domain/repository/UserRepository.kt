package com.example.pillinTimeAndroid.domain.repository

import com.example.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.example.pillinTimeAndroid.data.remote.dto.UserDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.CabinetRequest
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import java.util.Objects

interface UserRepository {
    suspend fun initClient(): Result<BaseResponse<UserDTO<List<RelationDTO>>>>
    suspend fun getUserInfo(): Result<BaseResponse<UserDTO<Any>>>
    suspend fun patchUserInfo(accessToken: String, signInRequest: SignInRequest): Result<BaseResponse<UserDTO<Any>>>
    suspend fun deleteUserInfo(): Result<BaseResponse<Any>>
    suspend fun postRegisterCabinet(accessToken: String, cabinetRequest: CabinetRequest): Result<BaseResponse<Objects>>
}