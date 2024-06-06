package com.example.pillinTimeAndroid.presentation.mypage

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.presentation.nvgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val localUserDataSource: LocalUserDataSource
) : ViewModel() {
    private val _navigateToScreen = MutableStateFlow<String?>(null)
    val navigateToScreen = _navigateToScreen.asStateFlow()

    fun signOut(navController: NavController, context: Context) {
        viewModelScope.launch {
            localUserDataSource.deleteAccessToken()
            localUserDataSource.saveUserName("")
            navController.navigate(Route.SignInScreen.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
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