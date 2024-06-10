package com.whdaud.pillinTimeAndroid.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.whdaud.pillinTimeAndroid.domain.repository.SignInRepository
import com.whdaud.pillinTimeAndroid.presentation.nvgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val signInRepository: SignInRepository
) : ViewModel() {
    private val _navigateToScreen = MutableStateFlow<String?>(null)
    val navigateToScreen = _navigateToScreen.asStateFlow()

    fun signOut(navController: NavController, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val result = signInRepository.signOut()
            result.onSuccess {
                if(it.status == 200) {
                    navController.navigate(Route.SignInScreen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                } else {
                    onResult("네트워크 에러 발생 다시 시도해주세요")
                }
            }.onFailure {
                onResult("알 수 없는 오류 발생 다시 시도해주세요")
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