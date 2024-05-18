package com.example.pillinTimeAndroid.data.remote

import com.example.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.RelationRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.RelationReqResponse
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RelationService {
    @POST("/api/request")
    suspend fun postRelationRequest(
        @Header("Authorization") accessToken: String,
        @Body relationRequest: RelationRequest
    ): BaseResponse<RelationDTO>

    @GET("/api/request")
    suspend fun getRelationRequest(
        @Header("Authorization") accessToken: String
    ): BaseResponse<List<RelationReqResponse>>


    @GET("/api/relation")
    suspend fun getRelations(
        @Header("Authorization") accessToken: String
    ): BaseResponse<List<RelationDTO>>

    @POST("/api/relation")
    suspend fun postRelation(
        @Header("Authorization") accessToken: String,
        @Query("requestId") requestId: Int
    ): BaseResponse<Any>
}