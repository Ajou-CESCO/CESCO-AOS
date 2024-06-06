package com.example.pillinTimeAndroid.presentation.mypage.withdrawal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import com.example.pillinTimeAndroid.presentation.nvgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localUserDataSource: LocalUserDataSource
) : ViewModel() {

    fun deleteUserInfo(navController: NavController) {
        viewModelScope.launch {
            val result = userRepository.deleteUserInfo()
            result.onSuccess {
                Log.d("withdrawal Screen", "Succeeded to delete userInfo: ${it.result}")
                localUserDataSource.deleteAccessToken()
                navController.navigate(Route.SignInScreen.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }.onFailure {
                Log.e("withdrawal Screen", "Failed to delete userInfo: ${it.message}")
            }
        }
    }
}