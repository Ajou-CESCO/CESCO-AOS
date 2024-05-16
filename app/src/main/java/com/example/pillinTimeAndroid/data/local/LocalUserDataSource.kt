package com.example.pillinTimeAndroid.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pillinTimeAndroid.domain.entity.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val Context.dataStore by preferencesDataStore(name = "user_preferences")
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val NAME = stringPreferencesKey("name")
        private val PHONE = stringPreferencesKey("phone")
        private val SSN = stringPreferencesKey("ssn")
        private val USER_TYPE = intPreferencesKey("user_type")
    }

    fun readUserSession(): Flow<User?> {
        return context.dataStore.data.map { preferences ->
            val accessToken = preferences[ACCESS_TOKEN]
            val refreshToken = preferences[REFRESH_TOKEN]
            val name = preferences[NAME]
            val phone = preferences[PHONE]
            val ssn = preferences[SSN]
            val userType = preferences[USER_TYPE]
            Log.d("accessToken", accessToken.toString())
            if (accessToken != null) {
                User(name, phone, ssn, accessToken, refreshToken, userType)
            } else {
                null
            }
        }
    }

    suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun deleteAccessToken() {
        context.dataStore.edit {preferences ->
            preferences.remove(ACCESS_TOKEN)
        }
    }

    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME] = name
        }
    }

    suspend fun saveUserPhone(phone: String) {
        context.dataStore.edit { preferences ->
            preferences[PHONE] = phone
        }
    }
    suspend fun saveUserSsn(ssn: String) {
        context.dataStore.edit { preferences ->
            preferences[SSN] = ssn
        }
    }
    suspend fun saveUserType(userType: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_TYPE] = userType
        }
    }

    fun getAccessToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }
    }

    fun getUserName(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[NAME]
        }
    }

    fun getUserPhone(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[PHONE]
        }
    }

    fun getUserSsn(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[SSN]
        }
    }
}