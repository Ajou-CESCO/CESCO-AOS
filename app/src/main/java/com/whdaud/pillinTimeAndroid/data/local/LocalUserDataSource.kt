package com.whdaud.pillinTimeAndroid.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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
        private val FCM_TOKEN = stringPreferencesKey("fcm_token")
        private val NAME = stringPreferencesKey("name")
        private val PHONE = stringPreferencesKey("phone")
        private val SSN = stringPreferencesKey("ssn")
        private val APP_ENTRY = booleanPreferencesKey("app_entry")
    }

    val appEntry: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[APP_ENTRY] ?: false
    }

    suspend fun saveAppEntry() {
        context.dataStore.edit { preferences ->
            preferences[APP_ENTRY] = true
        }
    }

    suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun deleteAccessToken() {
        context.dataStore.edit { preferences ->
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