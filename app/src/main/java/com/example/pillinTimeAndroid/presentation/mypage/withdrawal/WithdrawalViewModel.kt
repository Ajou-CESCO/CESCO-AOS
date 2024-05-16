package com.example.pillinTimeAndroid.presentation.mypage.withdrawal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localUserDataSource: LocalUserDataSource
) : ViewModel() {

    fun deleteUserInfo() {
        viewModelScope.launch {
            val accessToken = localUserDataSource.getAccessToken().firstOrNull()
            val result = userRepository.deleteUserInfo("Bearer $accessToken")
            result.onSuccess {
                Log.d("withdrawal Screen", "Succeeded to delete userInfo: ${it.result}")
                localUserDataSource.deleteAccessToken()
            }.onFailure {
                Log.e("withdrawal Screen", "Failed to delete userInfo: ${it.message}")
            }
        }
    }
}