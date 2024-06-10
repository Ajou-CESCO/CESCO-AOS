package com.whdaud.pillinTimeAndroid.presentation.mypage.cabinet

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.CabinetRequest
import com.whdaud.pillinTimeAndroid.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CabinetViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private var serialNumber = mutableStateOf("")

    fun postRegisterCabinet(ownerId: Int, navController: NavController) {
        viewModelScope.launch {
            val cabinetInfo = CabinetRequest(
                serial = serialNumber.value,
                ownerId = ownerId
            )
            val result = userRepository.postRegisterCabinet(cabinetInfo)
            Log.e("CabinetViewModel", "$cabinetInfo")
            result.onSuccess {
                Log.d("CabinetViewModel", "Succeeded to register: ${it.result}")
                navController.popBackStack()
            }.onFailure {
                Log.e("CabinetViewModel", "Failed to register: ${it.message}")
            }
        }
    }

    fun getCurrentInput(): String {
        return serialNumber.value
    }

    fun updateInput(input: String) {
        serialNumber.value = input
    }

    fun isValidateInput(): Boolean {
        val regex = Regex("^[a-z0-9]{16}\$")
        return serialNumber.value.matches(regex) || serialNumber.value.isEmpty()
    }
}