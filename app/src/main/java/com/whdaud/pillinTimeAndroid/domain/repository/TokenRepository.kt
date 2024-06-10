package com.whdaud.pillinTimeAndroid.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun loadAccessToken(): Flow<String?>
    suspend fun saveAccessToken(accessToken: String)
    suspend fun deleteAccessToken()
}