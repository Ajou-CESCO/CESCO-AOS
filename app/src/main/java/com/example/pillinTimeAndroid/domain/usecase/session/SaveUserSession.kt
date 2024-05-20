package com.example.pillinTimeAndroid.domain.usecase.session

import com.example.pillinTimeAndroid.data.local.LocalUserDataSource

class SaveUserSession(
    private val localUserDataSource: LocalUserDataSource
) {
    suspend operator fun invoke(accessToken: String) {
        localUserDataSource.saveAccessToken(accessToken)
    }
}