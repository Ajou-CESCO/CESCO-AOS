package com.example.pillinTimeAndroid.presentation.schedule

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.example.pillinTimeAndroid.data.remote.dto.UserDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.ScheduleRequest
import com.example.pillinTimeAndroid.domain.repository.MedicineRepository
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import com.example.pillinTimeAndroid.presentation.schedule.components.ScheduleOrderList
import com.example.pillinTimeAndroid.presentation.schedule.components.schedulePages
import com.example.pillinTimeAndroid.presentation.signin.components.signInPages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val localUserDataSource: LocalUserDataSource,
    private val userRepository: UserRepository,
    private val medicineRepository: MedicineRepository
) : ViewModel() {
    private val _userDetails = MutableStateFlow<UserDTO?>(null)
    val userDetails = _userDetails.asStateFlow()

    val userList = listOf("김종명", "노수인", "심재민", "이재현", "김학준", "김종명", "노수인", "심재민")

    private val currentPageIndex = mutableIntStateOf(0)
    private val _medicineInput = MutableStateFlow("")
    val medicineInput = _medicineInput.asStateFlow()

    private val _medicineInfo = MutableStateFlow<List<MedicineDTO>>(emptyList())
    val medicineInfo = _medicineInfo.asStateFlow()

    private val _searchStatus = MutableStateFlow(false)
    val searchStatus = _searchStatus.asStateFlow()

    private val _doseLog = MutableStateFlow<List<ScheduleLogDTO>>(emptyList())
    val doseLog = _doseLog.asStateFlow()

    val selectedMedicine = mutableStateOf<MedicineDTO?>(null)
    val selectedDays = mutableStateListOf<Int>()
    val selectedTimes = mutableStateListOf<String>()
    val scheduleStartDate = mutableStateOf("")
    val scheduleEndDate = mutableStateOf("")

    init {
        getUserDoseLog()
        getUserInfo()
        _medicineInput.debounce(500)
//            .filter { input -> input.isNotBlank() }
            .distinctUntilChanged().flatMapLatest { medicineName ->
                flow {
                    emit(getMedicineInfo(medicineName))
                }
            }.launchIn(viewModelScope)
    }

    private fun getUserDoseLog() {
        viewModelScope.launch {
            val accessToken = localUserDataSource.getAccessToken().firstOrNull().orEmpty()
            val result = medicineRepository.getDoseLog("Bearer $accessToken", 2)
            result.onSuccess {
                _doseLog.value = it.result
                Log.e("getUserDoseLog", "Succeeded to fetch: ${it.result}")
            }.onFailure {
                Log.e("getUserDoseLog", "Failed to fetch user details: ${it.message}")
            }
        }
    }
    private fun getUserInfo() {
        viewModelScope.launch {
            val accessToken = localUserDataSource.getAccessToken().firstOrNull().orEmpty()
            Log.d("HomeViewModel", "Access Token: $accessToken")
            val result = userRepository.getUserInfo("Bearer $accessToken")
            result.onSuccess {
                _userDetails.value = it.result
                Log.d("HomeViewModel", "Succeeded to fetch: ${it.result}")
            }.onFailure {
                Log.e("HomeViewModel", "Failed to fetch user details: ${it.message}")
            }
        }
    }

    private fun getMedicineInfo(medicineName: String) {
        viewModelScope.launch {
            val accessToken = localUserDataSource.getAccessToken().firstOrNull().orEmpty()
            val trimmedName = medicineName.trim().replace(" ", "")
            val result = medicineRepository.getMedicineInfo("Bearer $accessToken", trimmedName)
            _searchStatus.value = medicineName.isNotEmpty()
            result.onSuccess {
                Log.d("getMedicineInfo", "Succeeded to fetch: ${it.result}")
                _medicineInfo.value = it.result
            }.onFailure {
                Log.e("getMedicineInfo", "Failed to fetch: ${it.message}")
                _medicineInfo.value = emptyList()
            }
        }
    }

    fun postDoseSchedule() {
        viewModelScope.launch {
            val accessToken = localUserDataSource.getAccessToken().firstOrNull().orEmpty()
            if (selectedMedicine.value != null) {
                val scheduleRequest =
                    ScheduleRequest(
                        memberId = 2,
                        medicineId = selectedMedicine.value!!.medicineCode,
                        weekdayList = selectedDays,
                        timeList = selectedTimes,
                        startAt = scheduleStartDate.value,
                        endAt = scheduleEndDate.value
                    )
                val result =
                    medicineRepository.postDoseSchedule("Bearer $accessToken", scheduleRequest)
                Log.e("request schedule",scheduleRequest.toString())
                result.onSuccess {
                    Log.d("getMedicineInfo", "Succeeded to fetch: ${it.result}")
                }.onFailure {
                    Log.e("getMedicineInfo", "Failed to fetch: ${it.message}")
                }
            }
        }
    }

    fun getCurrentPage(): ScheduleOrderList {
        return schedulePages.getOrElse(currentPageIndex.intValue) { schedulePages[0] }
    }

    fun getCurrentPageIndex(): Int {
        return currentPageIndex.intValue
    }

    fun getProgress(): Float {
        val totalPages = schedulePages.size
        val currentPage = currentPageIndex.intValue

        return (currentPage + 1).toFloat() / totalPages.toFloat()
    }

    fun getCurrentInput(): String {
        return _medicineInput.value
    }

    fun updateInput(input: String) {
        _medicineInput.value = input
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

    fun selectMedicine(medicine: MedicineDTO) {
        selectedMedicine.value = medicine
    }

    fun selectDays(dayIndex: Int) {
        if (selectedDays.contains(dayIndex)) {
            selectedDays.remove(dayIndex)
        } else {
            selectedDays.add(dayIndex)
        }
    }

    fun selectTimes(timeIndex: String) {
        if (selectedTimes.contains(timeIndex)) {
            selectedTimes.remove(timeIndex)
        } else {
            selectedTimes.add(timeIndex)
        }
    }

    fun checkButtonState(): Boolean {
        return when (currentPageIndex.intValue) {
            0 -> selectedMedicine.value != null
            1 -> selectedDays.isNotEmpty()
            2 -> selectedDays.isNotEmpty() && selectedTimes.isNotEmpty()
            3 -> scheduleStartDate.value.isNotEmpty() && scheduleEndDate.value.isNotEmpty()
            else -> true
        }
    }
}