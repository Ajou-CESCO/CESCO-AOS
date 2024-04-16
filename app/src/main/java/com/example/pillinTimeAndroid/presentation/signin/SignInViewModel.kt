package com.example.pillinTimeAndroid.presentation.signin

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    private var name = mutableStateOf("")
    private var phoneNumber = mutableStateOf("")
    private var otp = mutableStateOf("")
    var currentPageIndex = mutableStateOf(0)

    fun getCurrentPage(): InPage {
        return inPages.getOrElse(currentPageIndex.value) { inPages[0] }
    }

    fun getCurrentInput(): String {
        return when (currentPageIndex.value) {
            0 -> name.value
            1 -> phoneNumber.value
            2 -> otp.value
            else -> ""
        }
    }

    fun updateInput(input: String) {
        when (currentPageIndex.value) {
            0 -> name.value = input
            1 -> phoneNumber.value = input
            2 -> otp.value = input
        }
    }

    fun validateInput(): Boolean {
        return when (currentPageIndex.value) {
            0 -> name.value.matches(Regex("^[가-힣a-zA-Z]{1,}$")) || name.value.isEmpty()
            1 -> phoneNumber.value.matches(Regex("^01[0-1,7]-?[0-9]{3,4}-?[0-9]{4}$")) || phoneNumber.value.isEmpty()
            2 -> otp.value.matches(Regex("\\d{6}")) || otp.value.isEmpty()
            // otp는 추후 수정 필요
            else -> false
        }
    }

    fun getKeyboardOptions(): KeyboardOptions {
        return when (currentPageIndex.value) {
            1 -> KeyboardOptions(keyboardType = KeyboardType.Number)
            2 -> KeyboardOptions(keyboardType = KeyboardType.Number)
            else -> KeyboardOptions.Default
        }
    }

    fun nextPage() {
        if (currentPageIndex.value < inPages.size - 1) {
            currentPageIndex.value += 1
        }
    }

    fun previousPage() {
        if (currentPageIndex.value > 0) {
            currentPageIndex.value -= 1
        }
    }
}