package com.example.pillinTimeAndroid.presentation.mypage.editschedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleDTO
import com.example.pillinTimeAndroid.domain.entity.User
import com.example.pillinTimeAndroid.domain.repository.MedicineRepository
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditScheduleViewModel @Inject constructor(
    private val medicineRepository: MedicineRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _relationInfoList = MutableStateFlow<List<RelationDTO>>(emptyList())
    val relationInfoList: StateFlow<List<RelationDTO>> = _relationInfoList

    private val _userDetails = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _userDetails

    private val _doseSchedule = MutableStateFlow<List<ScheduleDTO>>(emptyList())
    val doseSchedule: StateFlow<List<ScheduleDTO>> = _doseSchedule

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> get() = _toastMessage

    init {
        getInit()
    }
    private fun getInit() {
        viewModelScope.launch {
            val result = userRepository.initClient()
            result.onSuccess { response ->
                Log.e("EditScheduleViewModel", "success getinit: ${response.message}")
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
                Log.e("EditScheduleViewModel", "failed getinit: ${it.message}")
            }
        }
    }

    fun getDoseSchedule(memberId: Int) {
        viewModelScope.launch {
            val result = medicineRepository.getDoseSchedule(memberId)
            result.onSuccess {response ->
                _doseSchedule.value = response.result
                Log.e("EditScheduleViewModel", "succeed to fetch dose schedule")
            }.onFailure {
                Log.e("EditScheduleViewModel", "Failed to fetch dose schedule: ${it.message}")
            }
        }
    }

    fun deleteDoseSchedule(memberId: Int, medicineId: String, cabinetIndex: Int) {
        viewModelScope.launch {
            val result = medicineRepository.deleteDoseSchedule(memberId, medicineId, cabinetIndex)
            result.onSuccess {
                Log.e("EditScheduleViewModel", "succeed to delete dose schedule")
                getDoseSchedule(memberId)
                showToast("성공적으로 복약 계획을 삭제하였습니다")
                delay(2000)
                showToast("")
            }.onFailure {
                Log.e("EditScheduleViewModel", "Failed to delete dose schedule: ${it.message}")
            }
        }
    }

    private suspend fun showToast(message: String) {
        _toastMessage.emit(message)
    }
}