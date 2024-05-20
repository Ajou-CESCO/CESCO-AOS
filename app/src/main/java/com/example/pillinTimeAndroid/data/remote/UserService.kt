package com.example.pillinTimeAndroid.data.remote

import com.example.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.example.pillinTimeAndroid.data.remote.dto.TokenDTO
import com.example.pillinTimeAndroid.data.remote.dto.UserDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.example.pillinTimeAndroid.data.remote.dto.request.SignUpRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserService {

    @GET("/api/init")
    suspend fun initClient(
        @Header("Authorization") accessToken: String
    ): BaseResponse<UserDTO<List<RelationDTO>>>

    @POST("/api/user")
    suspend fun postUser(
        @Body signUpRequest: SignUpRequest
    ): BaseResponse<TokenDTO>

    @GET("/api/user")
    suspend fun getUserInfo(
        @Header("Authorization") accessToken: String
    ): BaseResponse<UserDTO<Any>>
    @DELETE("/api/user")
    suspend fun deleteUserInfo(
        @Header("Authorization") accessToken: String
    ): BaseResponse<Any>

    @PATCH("/api/user")
    suspend fun patchUserInfo(
        @Header("Authorization") accessToken: String,
        @Body signInRequest: SignInRequest
    ): BaseResponse<UserDTO<Any>>
}