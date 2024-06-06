package com.example.pillinTimeAndroid.data.repository

import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource
) : TokenRepository {
    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken: StateFlow<String?> = _accessToken

    override fun loadAccessToken(): Flow<String?> {
        return localUserDataSource.getAccessToken()
    }

    override suspend fun saveAccessToken(accessToken: String) {
        localUserDataSource.saveAccessToken(accessToken)
    }

    override suspend fun deleteAccessToken() {
        localUserDataSource.deleteAccessToken()
    }
}