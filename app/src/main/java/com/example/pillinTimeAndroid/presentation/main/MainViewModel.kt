package com.example.pillinTimeAndroid.presentation.main

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillinTimeAndroid.data.remote.dto.FCMTokenDTO
import com.example.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.example.pillinTimeAndroid.domain.entity.User
import com.example.pillinTimeAndroid.domain.repository.FcmRepository
import com.example.pillinTimeAndroid.domain.repository.MedicineRepository
import com.example.pillinTimeAndroid.domain.repository.RelationRepository
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import com.example.pillinTimeAndroid.domain.usecase.session.ReadUserSession
import com.example.pillinTimeAndroid.presentation.nvgraph.Route
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val readUserSession: ReadUserSession,
    private val userRepository: UserRepository,
    private val medicineRepository: MedicineRepository,
    private val relationRepository: RelationRepository,
    private val fcmRepository: FcmRepository
) : ViewModel() {
    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Route.SignInScreen.route)
    val startDestination: State<String> = _startDestination

    private val _userDetails = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _userDetails

    private val _relationInfoList = MutableStateFlow<List<RelationDTO>>(emptyList())
    val relationInfoList: StateFlow<List<RelationDTO>> = _relationInfoList

    private val _userDoseLog = MutableStateFlow<List<ScheduleLogDTO>>(emptyList())
    val userDoseLog: StateFlow<List<ScheduleLogDTO>> = _userDoseLog

    init {
        postFcmToken()
        viewModelScope.launch {
            val hasToken = readUserSession().firstOrNull().orEmpty().isNotEmpty()
            if (hasToken) {
                val result = userRepository.initClient()
                result.onSuccess { response ->
                    Log.d("MainViewModelREAAAAAL MAAAAIN", "succeed to init: ${response.message}")
                    _userDetails.value = User(
                        memberId = response.result.memberId,
                        name = response.result.name,
                        ssn = response.result.ssn,
                        phone = response.result.phone,
                        cabinetId = response.result.cabinetId,
                        isManager = response.result.isManager,
                        isSubscriber = response.result.isSubscriber
                    )
                    _relationInfoList.value = response.result.relationList
                    if (!response.result.isManager && response.result.relationList.isEmpty()) {
                        _startDestination.value = Route.SignUpClientScreen.route
                    } else if (response.result.isManager || response.result.relationList.isNotEmpty()) {
                        _startDestination.value = Route.BottomNavigatorScreen.route
                    }
                }.onFailure {
                    Log.d("MainViewModel", "failed to init: ${it.cause}")
                }
            } else {
                _startDestination.value = Route.SignInScreen.route
            }
            delay(300)
            _splashCondition.value = false
        }
    }
    private fun postFcmToken() {
        viewModelScope.launch {
            val token = Firebase.messaging.token.await()
            Log.d("FCM token:", token)
            val result = fcmRepository.postFcmToken(FCMTokenDTO(token))
            result.onSuccess {
                Log.e("MainViewModel FCM Token", "succeeded to post FCM Token: ${it.message}")
            }.onFailure {
                Log.e("MainViewModel FCM Token", "failed to post FCM Token: ${it.cause}")
            }
        }
    }

    fun getInitData() {
        viewModelScope.launch {
            val result = userRepository.initClient()
            result.onSuccess { response ->
                Log.d("MainViewModelV2", "succeed to init: ${response.message}")
                _userDetails.value = User(
                    memberId = response.result.memberId,
                    name = response.result.name,
                    ssn = response.result.ssn,
                    phone = response.result.phone,
                    cabinetId = response.result.cabinetId,
                    isManager = response.result.isManager,
                    isSubscriber = response.result.isSubscriber
                )
                _relationInfoList.value = response.result.relationList
            }.onFailure {
                Log.d("MainViewModelV2", "failed to init: ${it.cause}")
            }
        }
    }

    fun getUserDoseLog(memberId: Int) {
        viewModelScope.launch {
            val result = medicineRepository.getDoseLog(memberId)
            result.onSuccess {
                _userDoseLog.value = it.result
                Log.e("getUserDoseLog in main", "Succeeded to fetch: ${it.result}")
            }.onFailure {
                Log.e("getUserDoseLog in main", "Failed to fetch user details: ${it.message}")
            }
        }
    }

    fun getRelationship() {
        viewModelScope.launch {
            val result = relationRepository.getRelations()
            result.onSuccess { response ->
                _relationInfoList.value = response.result
            }
        }
    }

    fun removeRelation(relation: RelationDTO) {
        _relationInfoList.value = _relationInfoList.value.toMutableList().apply { remove(relation) }
    }}