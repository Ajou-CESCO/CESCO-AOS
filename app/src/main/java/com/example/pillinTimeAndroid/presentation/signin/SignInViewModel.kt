package com.example.pillinTimeAndroid.presentation.signin

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.example.pillinTimeAndroid.domain.entity.User
import com.example.pillinTimeAndroid.domain.repository.SignInRepository
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import com.example.pillinTimeAndroid.presentation.common.InputType
import com.example.pillinTimeAndroid.presentation.signin.components.SignInPageList
import com.example.pillinTimeAndroid.presentation.signin.components.signInPages
import com.example.pillinTimeAndroid.util.PhoneVisualTransformation
import com.example.pillinTimeAndroid.util.SsnVisualTransformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInRepository: SignInRepository,
    private val userRepository: UserRepository,
    private val localUserDataSource: LocalUserDataSource,
) : ViewModel() {
    private var phone = mutableStateOf("")
    private var otp = mutableStateOf("")
    private var name = mutableStateOf("")
    private var ssn = mutableStateOf("")
    private var currentPageIndex = mutableIntStateOf(0)

    fun signIn(navController: NavController) {
        val formattedPhone = "${phone.value.substring(0, 3)}-${phone.value.substring(3, 7)}-${phone.value.substring(7, 11)}"
        val formattedSsn = "${ssn.value.substring(0, 6)}-${ssn.value.substring(6)}"

        viewModelScope.launch {
            val result = signInRepository.signIn(
                SignInRequest(name.value, formattedPhone, formattedSsn)
            )
            result.onSuccess { authenticateResponse ->
                val user = User(accessToken = authenticateResponse.result.accessToken)
                viewModelScope.launch {
                    userRepository.saveUserSession(user)
                    navController.navigate("bottomNavigation")
                }
                Log.d("Login Success", "accessToken: , ${authenticateResponse.result.accessToken}")
            }.onFailure {
                val user = User(name = name.value, phone = formattedPhone, ssn = formattedSsn)
                viewModelScope.launch {
                    userRepository.saveUserSession(user)
//                    localUserDataSource.getUserName().collect { userName ->
//                        localUserDataSource.getUserPhone().collect { userPhone ->
//                            localUserDataSource.getUserSsn().collect { userSsn ->
//                                Log.d("User Info", "Name: $userName, Phone: $userPhone, SSN: $userSsn")
//                            }
//                        }
//                    }
                    Log.d("Login Fail: ", name.value + formattedPhone + result.toString())
                    navController.navigate("roleSelectScreen")
                }
            }
        }
    }

    fun getCurrentPage(): SignInPageList {
        return signInPages.getOrElse(currentPageIndex.intValue) { signInPages[0] }
    }

    fun getCurrentPageTitle(): String {
        val page = getCurrentPage()
        return page.title.replace("{name}", name.value)
    }

    fun getCurrentInput(): String {
        return when (currentPageIndex.intValue) {
            0 -> phone.value
            1 -> otp.value
            2 -> name.value
            3 -> ssn.value
            else -> ""
        }
    }

    fun getInputType(): InputType {
        return when (currentPageIndex.intValue) {
            0 -> InputType.PHONE
            1 -> InputType.OTP
            2 -> InputType.NAME
            3 -> InputType.SSN
            else -> InputType.PLAIN
        }
    }

    fun getVisualTransformations(): (AnnotatedString) -> TransformedText {
        val transformation = when (currentPageIndex.intValue) {
            0 -> PhoneVisualTransformation()
            3 -> SsnVisualTransformation()
            else -> VisualTransformation.None
        }
        return { input -> transformation.filter(input) }
    }

    fun updateInput(input: String) {
        when (currentPageIndex.intValue) {
            0 -> phone.value = input
            1 -> otp.value = input
            2 -> name.value = input
            3 -> ssn.value = input
        }
    }

    fun isValidateInput(): Boolean {
        val ssnRegex = Regex("^[0-9]{6}-?[1-4]$")
        return when (currentPageIndex.intValue) {
            0 -> phone.value.matches(Regex("^01[0-1,7]-?[0-9]{4}-?[0-9]{4}$")) || phone.value.isEmpty()
            1 -> otp.value.matches(Regex("\\d{6}")) || otp.value.isEmpty()
            2 -> name.value.matches(Regex("^[가-힣a-zA-Z]{1,}$")) || name.value.isEmpty()
            3 -> ssn.value.matches(ssnRegex) || ssn.value.isEmpty()
            else -> false
        }
    }

    fun nextPage() {
        if (currentPageIndex.intValue < signInPages.size - 1) {
            currentPageIndex.intValue++
        }
    }

    fun previousPage() {
        if (currentPageIndex.intValue > 0) {
            currentPageIndex.intValue--
        }
    }
}