package com.example.pillinTimeAndroid.data.repository

import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.domain.entity.User
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource
) : UserRepository {
    override suspend fun saveUserSession(user: User) {
        user.accessToken?.let { localUserDataSource.saveAccessToken(it) }
        user.name?.let { localUserDataSource.saveUserName(it) }
        user.phone?.let { localUserDataSource.saveUserPhone(it) }
        user.ssn?.let { localUserDataSource.saveUserSsn(it) }
        user.phone?.let { localUserDataSource.saveUserPhone(it) }
        user.userType?.let { localUserDataSource.saveUserType(it) }
    }

    override fun readUserSession(): Flow<User?> {
        return localUserDataSource.readUserSession()
    }
}