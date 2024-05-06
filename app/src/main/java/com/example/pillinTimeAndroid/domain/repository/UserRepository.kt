package com.example.pillinTimeAndroid.domain.repository

import com.example.pillinTimeAndroid.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUserSession(user: User)
    fun readUserSession(): Flow<User?>

}