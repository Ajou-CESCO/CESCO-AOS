package com.example.pillinTimeAndroid.domain.usecase.session

import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadUserSession @Inject constructor(
    private val localUserDataSource: LocalUserDataSource
) {
    operator fun invoke(): Flow<String?> {
        return localUserDataSource.getAccessToken()
    }
}