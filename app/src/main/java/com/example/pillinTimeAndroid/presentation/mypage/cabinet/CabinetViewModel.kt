package com.example.pillinTimeAndroid.presentation.mypage.cabinet

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillinTimeAndroid.data.remote.dto.request.CabinetRequest
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CabinetViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private var serialNumber = mutableStateOf("")

    fun postRegisterCabinet(ownerId: Int) {
        viewModelScope.launch {
            val cabinetInfo = CabinetRequest(
                serial = serialNumber.value,
                ownerId = ownerId
            )
            val result = userRepository.postRegisterCabinet(cabinetInfo)
            result.onSuccess {
                Log.d("CabinetViewModel", "Succeeded to register: ${it.result}")
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