package com.example.pillinTimeAndroid.domain.repository

import com.example.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.RelationRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse

interface RelationRepository {
    suspend fun postRelationRequest(accessToken: String, request: RelationRequest): BaseResponse<RelationDTO>
    suspend fun getRelationRequest(accessToken: String): Result<BaseResponse<List<RelationDTO>>>
    suspend fun getRelations(accessToken: String): Result<BaseResponse<List<RelationDTO>>>
}