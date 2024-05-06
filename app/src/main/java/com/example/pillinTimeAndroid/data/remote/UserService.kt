package com.example.pillinTimeAndroid.data.remote

import com.example.pillinTimeAndroid.data.remote.dto.TokenDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.SignUpRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("/api/user")
    suspend fun postUser(
        @Body signUpRequest: SignUpRequest
    ): BaseResponse<TokenDTO>

    @DELETE("/api/user")
    suspend fun deleteUser(
        @Header("access_token") accessToken: String
    ): BaseResponse<TokenDTO>
}