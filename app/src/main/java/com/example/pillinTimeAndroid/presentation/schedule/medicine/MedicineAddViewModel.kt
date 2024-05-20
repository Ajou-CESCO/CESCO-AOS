package com.example.pillinTimeAndroid.presentation.schedule.medicine

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.ScheduleRequest
import com.example.pillinTimeAndroid.domain.repository.MedicineRepository
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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MedicineAddViewModel @Inject constructor(
    private val medicineRepository: MedicineRepository
) : ViewModel() {
    private val currentPageIndex = mutableIntStateOf(0)
    private val _medicineInfo = MutableStateFlow<List<MedicineDTO>>(emptyList())
    val medicineInfo = _medicineInfo.asStateFlow()
    private val _medicineInput = MutableStateFlow("")
    val medicineInput = _medicineInput.asStateFlow()
    private val _searchStatus = MutableStateFlow(false)
    val searchStatus = _searchStatus.asStateFlow()

    val selectedMedicine = mutableStateOf<MedicineDTO?>(null)
    val selectedDays = mutableStateListOf<Int>()
    val selectedTimes = mutableStateListOf<String>()
    val scheduleStartDate = mutableStateOf("")
    val scheduleEndDate = mutableStateOf("")

    init {
        _medicineInput
            .debounce(500)
            .distinctUntilChanged()
            .flatMapLatest { medicineName ->
                if (medicineName.isNotEmpty()) {
                    flow {
                        val result = getMedicineInfo(medicineName)
                        emit(result)
                    }
                } else {
                    flow {
                        emit(emptyList<MedicineDTO>())
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getMedicineInfo(medicineName: String) {
        viewModelScope.launch {
            val trimmedName = medicineName.trim().replace(" ", "")
            val result = medicineRepository.getMedicineInfo(trimmedName)
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

    fun postDoseSchedule(memberId: Int, navController: NavController) {
        viewModelScope.launch {
            if (selectedMedicine.value != null) {
                val scheduleRequest =
                    ScheduleRequest(
                        memberId = memberId,
                        medicineId = selectedMedicine.value!!.medicineCode,
                        weekdayList = selectedDays.map { it + 1},
                        timeList = selectedTimes,
                        startAt = scheduleStartDate.value,
                        endAt = scheduleEndDate.value,
                    )
                val result =
                    medicineRepository.postDoseSchedule(scheduleRequest)
                Log.e("request schedule", scheduleRequest.toString())
                result.onSuccess {
                    Log.d("getMedicineInfo", "Succeeded to fetch: ${it.result}")
                    navController.navigate("scheduleScreen") {
                        popUpTo("scheduleScreen") {
                            inclusive = true
                        }
                    }
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