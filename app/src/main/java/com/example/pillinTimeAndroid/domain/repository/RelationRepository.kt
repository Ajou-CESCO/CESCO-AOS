package com.example.pillinTimeAndroid.domain.repository

import com.example.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.RelationRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.RelationReqResponse
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse

interface RelationRepository {
    suspend fun postRelationRequest(accessToken: String, request: RelationRequest): BaseResponse<RelationDTO>
    suspend fun getRelations(): Result<BaseResponse<List<RelationDTO>>>
    suspend fun postRelation(requestId: Int): Result<BaseResponse<Any>>
    suspend fun getRelationRequest(): Result<BaseResponse<List<RelationReqResponse>>>
}