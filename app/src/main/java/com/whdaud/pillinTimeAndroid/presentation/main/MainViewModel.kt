package com.whdaud.pillinTimeAndroid.presentation.main

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whdaud.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.ScheduleLog
import com.whdaud.pillinTimeAndroid.domain.entity.User
import com.whdaud.pillinTimeAndroid.domain.repository.MedicineRepository
import com.whdaud.pillinTimeAndroid.domain.repository.RelationRepository
import com.whdaud.pillinTimeAndroid.domain.repository.UserRepository
import com.whdaud.pillinTimeAndroid.domain.usecase.session.ReadUserSession
import com.whdaud.pillinTimeAndroid.presentation.nvgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val readUserSession: ReadUserSession,
    private val userRepository: UserRepository,
    private val medicineRepository: MedicineRepository,
    private val relationRepository: RelationRepository,
) : ViewModel() {
    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition
    private val _startDestination = mutableStateOf(Route.SignInScreen.route)
    val startDestination: State<String> = _startDestination

    private val _userDetails = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _userDetails
    private val _relationInfoList = MutableStateFlow<List<RelationDTO>>(emptyList())
    val relationInfoList: StateFlow<List<RelationDTO>> = _relationInfoList
    private val _userDoseLog = MutableStateFlow<List<ScheduleLog>>(emptyList())
    val userDoseLog: StateFlow<List<ScheduleLog>> = _userDoseLog
    private val _medicineInfoV2 = MutableStateFlow<MedicineDTO?>(null)
    val medicineInfoV2: StateFlow<MedicineDTO?> = _medicineInfoV2

    init {
        getRelationship()
        viewModelScope.launch {
            val hasToken = readUserSession().firstOrNull().orEmpty().isNotEmpty()
            if (hasToken) {
                val result = userRepository.initClient()
                result.onSuccess { response ->
                    Log.d("MainViewModel", "succeed to init: ${response.message}")
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
                    when (response.result.isManager) {
                        true -> { // when Manager
                            _startDestination.value = Route.BottomNavigatorScreen.route
                        }

                        false -> { // when Client
                            if (response.result.relationList.isEmpty()) {
                                _startDestination.value = Route.SignUpClientScreen.route
                            } else {
                                _startDestination.value = Route.BottomNavigatorScreen.route
                            }
                        }
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

    fun getUserDoseLog(memberId: Int, date: String = "") {
        viewModelScope.launch {
            val result = medicineRepository.getDoseLog(memberId, date)
            Log.e("getUserDoseLog in main", "${memberId}, ${date}")
            result.onSuccess {
                _userDoseLog.value = it.result.logList
                Log.e("getUserDoseLog in main", "Succeeded to fetch: ${it.result}")
            }.onFailure {
                Log.e("getUserDoseLog in main", "Failed to fetch user details: ${it.message}")
            }
        }
    }

    fun getMedicineInfoV2(medicineId: Int) {
        viewModelScope.launch {
            val result = medicineRepository.getMedicineInfoV2(medicineId)
            result.onSuccess {
                _medicineInfoV2.value = it.result
                Log.e("getUserDoseLog in main", "Succeeded to fetch: ${it.result}")
            }.onFailure {
                Log.e("MainVM", "failed getMedInfoV2: ${it.message}")
            }
        }
    }

    fun clearMedicineInfo() {
        _medicineInfoV2.value = null
    }

    fun getRelationship() {
        viewModelScope.launch {
            val result = relationRepository.getRelations()
            result.onSuccess { response ->

                Log.e("MainVM", "success getRelationShip: ${response.result}")
                _relationInfoList.value = response.result
                Log.e("MainVM", "success getRelationShip: ${_relationInfoList.value}")
            }.onFailure {
                Log.e("MainVM", "failed getRelationShip: ${it.message}")
            }
        }
    }

    fun removeRelation(relation: RelationDTO) {
        _relationInfoList.value = _relationInfoList.value.toMutableList().apply { remove(relation) }
    }
}