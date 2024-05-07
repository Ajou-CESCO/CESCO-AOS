package com.example.pillinTimeAndroid.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.data.remote.dto.UserDTO
import com.example.pillinTimeAndroid.domain.repository.RelationRepository
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localUserDataSource: LocalUserDataSource,
    private val userRepository: UserRepository,
    private val relationRepository: RelationRepository,
    ): ViewModel() {
    private val _userDetails = MutableStateFlow<UserDTO?>(null)
    val userDetails = _userDetails.asStateFlow()

    init {
        getUserInfo()
        getClientList()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            val accessToken = localUserDataSource.getAccessToken().firstOrNull().orEmpty()
            Log.d("HomeViewModel", "Access Token: $accessToken")
            val result = userRepository.getUserInfo("Bearer $accessToken")
            result.onSuccess {
                _userDetails.value = it.result
                Log.d("HomeViewModel", "Succeeded to fetch: ${it.result}")
            }.onFailure {
                Log.e("HomeViewModel", "Failed to fetch user details: ${it.message}")
            }
        }
    }

    private fun getClientList() {
        if(_userDetails.value?.inManager != true) {
            viewModelScope.launch {
                val accessToken = localUserDataSource.getAccessToken().firstOrNull().orEmpty()
                val result = relationRepository.getRelations("Bearer $accessToken")
                result.onSuccess {
                    Log.d("Relation", "Succeeded to fetch relation: ${it.result}")
                }.onFailure {
                    Log.e("Relation", "Failed to fetch user details: ${it.message}")
                }
            }
        }
    }
}