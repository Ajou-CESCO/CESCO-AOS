package com.example.pillinTimeAndroid.presentation.signup

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.SignUpRequest
import com.example.pillinTimeAndroid.domain.entity.User
import com.example.pillinTimeAndroid.domain.repository.RelationRepository
import com.example.pillinTimeAndroid.domain.repository.SignUpRepository
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import com.example.pillinTimeAndroid.presentation.signup.components.SignUpPageList
import com.example.pillinTimeAndroid.presentation.signup.components.clientPages
import com.example.pillinTimeAndroid.presentation.signup.components.managerPages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository,
    private val userRepository: UserRepository,
    private val relationRepository: RelationRepository,
    private val localUserDataSource: LocalUserDataSource
) : ViewModel() {
    private var currentPageIndex = mutableIntStateOf(0)
    private var currentPage: List<SignUpPageList> = emptyList()

    private val _userName = MutableStateFlow("")
    private val _userPhone = MutableStateFlow("")
    private val _userSsn = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(false)
    private val _managerRequest = MutableStateFlow<List<RelationDTO>>(emptyList())

    private var targetPhone = mutableStateOf("")
    val userName: StateFlow<String> = _userName.asStateFlow()
    var isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val managerRequest: StateFlow<List<RelationDTO>> = _managerRequest.asStateFlow()


    init {
        fetchManagerRequests()
    }

    fun signUp(navController: NavController, role: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            val name = localUserDataSource.getUserName().firstOrNull().orEmpty()
            val phone = localUserDataSource.getUserPhone().firstOrNull().orEmpty()
            val ssn = localUserDataSource.getUserSsn().firstOrNull().orEmpty()
            _userName.value = name
            _userPhone.value = phone
            _userSsn.value = ssn

            val userSignUpInfo = SignUpRequest(
                ssn = _userSsn.value,
                name = _userName.value,
                phone = _userPhone.value,
                userType  = role
                )
            Log.d("signup user info", "name: ${userSignUpInfo.name} / ssn: ${userSignUpInfo.ssn} / phone: ${userSignUpInfo.phone} / type: ${userSignUpInfo.userType}")
            val response = signUpRepository.signUp(userSignUpInfo)
            response.onSuccess { authenticateResponse ->
                Log.e("SignUp", "Succeed to sign up: ${authenticateResponse.status}")
                val user = User(
                    accessToken = authenticateResponse.result.accessToken,
                    ssn = _userSsn.value,
                    name = _userName.value,
                    phone = _userPhone.value,
                    userType = role
                )
                userRepository.saveUserSession(user)
                currentPage = when (role) {
                    0 -> managerPages
                    1 -> clientPages
                    else -> emptyList()
                }
                val destination = if (role == 0) "signupManagerScreen" else "signupClientScreen"
                delay(4000)
                navController.navigate(destination)
            }.onFailure {authenticateResponse ->
                Log.e("SignUp", "Failed to sign up: ${authenticateResponse.message}")
            }
        }
    }
    private fun fetchManagerRequests() {
        viewModelScope.launch {
            while (isActive) {
                getManagerRequest()
                delay(60000)
            }
        }
    }
    private fun getManagerRequest() {
        viewModelScope.launch {
            val accessToken = localUserDataSource.getAccessToken().firstOrNull()
            val response = accessToken?.let { relationRepository.getRelationRequest(it) }
            response?.onSuccess { requestResponse ->
                _managerRequest.value = requestResponse.result
            }?.onFailure { requestResponse ->
                Log.e("fetch Manager Request", "Failed to fetch requests: ${requestResponse.message}")
            }
        }
    }

    fun getCurrentPage(): SignUpPageList {
        return currentPage.getOrElse(currentPageIndex.intValue) { currentPage[0] }
    }

    fun getCurrentInput(): String {
        return when (currentPageIndex.intValue) {
            0 -> targetPhone.value
            else -> ""
        }
    }

    fun getCurrentPageTitle(): String {
        val page = getCurrentPage()
        return page.title.replace("{name}", _userName.value)
    }

    fun updateInput(input: String) {
        when (currentPageIndex.intValue) {
            0 -> targetPhone.value = input
        }
    }

    fun isValidateInput(): Boolean {
        return when (currentPageIndex.intValue) {
            0 -> targetPhone.value.matches(Regex("^01[0-1,7]-?[0-9]{3,4}-?[0-9]{4}$")) || targetPhone.value.isEmpty()
            else -> false
        }
    }
}