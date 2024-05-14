package com.example.pillinTimeAndroid.presentation.schedule

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.common.ButtonColor
import com.example.pillinTimeAndroid.presentation.common.ButtonSize
import com.example.pillinTimeAndroid.presentation.common.CustomButton
import com.example.pillinTimeAndroid.presentation.common.CustomTextField
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.presentation.common.CustomWeekCalendar
import com.example.pillinTimeAndroid.presentation.common.GeneralScreen
import com.example.pillinTimeAndroid.presentation.schedule.components.schedulePages
import com.example.pillinTimeAndroid.presentation.schedule.search.MedicineSearchResult
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme

@Composable
fun ScheduleAddScreen(
    viewModel: ScheduleViewModel,
    navController: NavController
) {
    val currentPage = viewModel.getCurrentPage()
    val medicineInput by viewModel.medicineInput.collectAsState()
    val medicineInfo by viewModel.medicineInfo.collectAsState()
    val searchStatus by viewModel.searchStatus.collectAsState()
    val selectedMedicine = viewModel.selectedMedicine
    val selectedDay = viewModel.selectedDay
    val selectedTime = viewModel.selectedTime

    GeneralScreen(
        topBar = {
            CustomTopBar(
                showProgressBar = true,
                progress = viewModel.getProgress(),
                showBackButton = true,
                onBackClicked = {
                    if (currentPage != schedulePages[0]) {
                        viewModel.previousPage()
                    } else {
                        navController.popBackStack()
                    }
                }
            )
        },
        title = currentPage.title,
        subtitle = currentPage.subtitle,
        content = {
            when(currentPage) {
                schedulePages[0] -> {
                    CustomTextField(
                        state = true,
                        hint = "의약품명 검색",
                        value = medicineInput,
                        onValueChange = viewModel::updateInput,
                        trailIcon = R.drawable.ic_search,
                    )
                    if (medicineInfo.isNotEmpty() && searchStatus) {
                        MedicineSearchResult(
                            medicineList = medicineInfo,
                            selectedMedicine = selectedMedicine.value,
                            onMedicineClick = { medicine ->
                                viewModel.selectMedicine(medicine)
                                Log.d("selectedMedicine", "${medicine.medicineName} ${viewModel.selectedMedicine.value?.medicineName}")
                            },
                        )
                    } else if (medicineInfo.isEmpty() && searchStatus) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(.6f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_pill_not_found),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "검색어를 다시 확인해주세요",
                                color = Gray90,
                                style = PillinTimeTheme.typography.caption1Bold
                            )
                            Text(
                                text = "검색 결과가 없습니다.",
                                color = Gray90,
                                style = PillinTimeTheme.typography.caption1Regular
                            )
                        }
                    } else {
                        MedicineSearchResult(
                            medicineList = emptyList(),
                            selectedMedicine = null,
                            onMedicineClick = { },
                        )
                    }
                }
                schedulePages[1], schedulePages[2] -> {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "복용하는 요일을 선택해주세요"
                        )
                        CustomWeekCalendar(
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    if (selectedTime.isNotEmpty()) {

                    }
                }
                schedulePages[3] -> {

                }
            }
        },
        button = {
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.checkButtonState(),
                filled = ButtonColor.FILLED,
                size = ButtonSize.MEDIUM,
                text = "다음",
                onClick = { viewModel.nextPage() }
            )
        }
    )
}