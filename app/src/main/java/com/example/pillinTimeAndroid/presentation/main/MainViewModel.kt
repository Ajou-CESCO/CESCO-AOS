package com.example.pillinTimeAndroid.presentation.main

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import com.example.pillinTimeAndroid.domain.usecase.session.ReadUserSession
import com.example.pillinTimeAndroid.presentation.nvgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val readUserSession: ReadUserSession,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Route.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            val hasToken = readUserSession().firstOrNull().orEmpty().isNotEmpty()
            if (hasToken) {
                val result = userRepository.initClient()
                result.onSuccess {
                    if (!it.result.isManager && it.result.relationList.isEmpty()) {
                        _startDestination.value = Route.AppStartNavigationV2.route
                    } else if (it.result.isManager || it.result.relationList.isNotEmpty()) {
                        _startDestination.value = Route.BottomNavigation.route
                    }
                }.onFailure {
                    Log.d("MainViewModel", "failed to init: ${it.cause}")
                }
            } else {
                _startDestination.value = Route.AppStartNavigation.route
            }
            delay(300)
            _splashCondition.value = false
        }
    }
}