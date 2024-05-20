package com.example.pillinTimeAndroid.presentation.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.data.remote.dto.UserDTO
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localUserDataSource: LocalUserDataSource,
) : ViewModel() {
    private val _userDetails = MutableStateFlow<UserDTO<Any>?>(null)
    val userDetails = _userDetails.asStateFlow()

    private val _navigateToScreen = MutableStateFlow<String?>(null)
    val navigateToScreen = _navigateToScreen.asStateFlow()
    init {
        getUserInfo()
    }
    private fun getUserInfo() {
        viewModelScope.launch {
            val accessToken = localUserDataSource.getAccessToken().firstOrNull().orEmpty()
            Log.d("HomeViewModel", "Access Token: $accessToken")
            val result = userRepository.getUserInfo()
            result.onSuccess {
                _userDetails.value = it.result
                Log.d("MyPageViewModel", "Succeeded to fetch: ${it.result}")
            }.onFailure {
                Log.e("MyPageViewModel", "Failed to fetch user details: ${it.message}")
            }
        }
    }



    fun navigateTo(destination: String) {
        _navigateToScreen.value = destination
    }
    fun clearNavigation() {
        _navigateToScreen.value = null
    }
}