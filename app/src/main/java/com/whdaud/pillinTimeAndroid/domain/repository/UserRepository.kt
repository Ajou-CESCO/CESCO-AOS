package com.whdaud.pillinTimeAndroid.domain.repository

import com.whdaud.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.UserDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.CabinetRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.HealthDataRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.HealthStatDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse

interface UserRepository {
    suspend fun initClient(): Result<BaseResponse<UserDTO<List<RelationDTO>>>>
    suspend fun getUserInfo(): Result<BaseResponse<UserDTO<Any>>>
    suspend fun patchUserInfo(accessToken: String, signInRequest: SignInRequest): Result<BaseResponse<UserDTO<Any>>>
    suspend fun deleteUserInfo(): Result<BaseResponse<Any>>
    suspend fun postRegisterCabinet(cabinetRequest: CabinetRequest): Result<BaseResponse<Any>>
    suspend fun deleteCabinet(cabinetId: Int): Result<BaseResponse<Any>>
    suspend fun getHealthData(memberId: Int): Result<BaseResponse<HealthStatDTO>>
    suspend fun postHealthData(healthDataRequest: HealthDataRequest): Result<BaseResponse<Any>>
}