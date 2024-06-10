package com.whdaud.pillinTimeAndroid.data.repository

import com.whdaud.pillinTimeAndroid.data.remote.RelationService
import com.whdaud.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.RelationReqRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.RelationReqResponse
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.whdaud.pillinTimeAndroid.domain.repository.RelationRepository
import com.whdaud.pillinTimeAndroid.domain.repository.TokenRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class RelationRepositoryImpl @Inject constructor(
    private val relationService: RelationService,
    private val tokenRepository: TokenRepository
) : RelationRepository {
    override suspend fun postRelationRequest(relationReqRequest: RelationReqRequest): Result<BaseResponse<Any>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = relationService.postRelationRequest("Bearer $accessToken", relationReqRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
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

    override suspend fun deleteRelation(relationId: Int): Result<BaseResponse<Any>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = relationService.deleteRelation("Bearer $accessToken", relationId)
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
            if (response.status == 200) {
                Result.success(response)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}