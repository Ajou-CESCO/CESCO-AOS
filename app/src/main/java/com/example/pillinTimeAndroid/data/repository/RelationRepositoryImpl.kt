package com.example.pillinTimeAndroid.data.repository

import com.example.pillinTimeAndroid.data.remote.RelationService
import com.example.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.RelationRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.RelationReqResponse
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.example.pillinTimeAndroid.domain.repository.RelationRepository
import com.example.pillinTimeAndroid.domain.repository.TokenRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class RelationRepositoryImpl @Inject constructor(
    private val relationService: RelationService, private val tokenRepository: TokenRepository
) : RelationRepository {
    override suspend fun postRelationRequest(
        accessToken: String, request: RelationRequest
    ): BaseResponse<RelationDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getRelationRequest(): Result<BaseResponse<List<RelationReqResponse>>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = relationService.getRelationRequest("Bearer $accessToken")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRelations(): Result<BaseResponse<List<RelationDTO>>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = relationService.getRelations("Bearer $accessToken")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postRelation(requestId: Int): Result<BaseResponse<Any>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = relationService.postRelation("Bearer $accessToken", requestId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}