package com.whdaud.pillinTimeAndroid.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.whdaud.pillinTimeAndroid.data.local.LocalUserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val localUserDataSource: LocalUserDataSource
) : ViewModel() {

    fun saveAppEntry(navController: NavController) {
        viewModelScope.launch {
            localUserDataSource.saveAppEntry()
            navController.navigate("bottomNavigatorScreen") {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }
    }
}