package com.example.pillinTimeAndroid.presentation.schedule.medicine

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.common.ButtonColor
import com.example.pillinTimeAndroid.presentation.common.ButtonSize
import com.example.pillinTimeAndroid.presentation.common.CustomButton
import com.example.pillinTimeAndroid.presentation.common.CustomLottieView
import com.example.pillinTimeAndroid.presentation.common.CustomTextField
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.presentation.common.CustomWeekCalendar
import com.example.pillinTimeAndroid.presentation.common.GeneralScreen
import com.example.pillinTimeAndroid.presentation.schedule.components.ScheduleDatePicker
import com.example.pillinTimeAndroid.presentation.schedule.components.ScheduleTimeButton
import com.example.pillinTimeAndroid.presentation.schedule.components.schedulePages
import com.example.pillinTimeAndroid.ui.theme.Error60
import com.example.pillinTimeAndroid.ui.theme.Gray20
import com.example.pillinTimeAndroid.ui.theme.Gray40
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary40
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.Success60
import com.example.pillinTimeAndroid.ui.theme.Warning60
import com.example.pillinTimeAndroid.util.fadeInEffect

@Composable
fun ScheduleAddScreen(
    viewModel: MedicineAddViewModel = hiltViewModel(),
    navController: NavController,
    memberId: Int?
) {
    val currentPage = viewModel.getCurrentPage()
    val currentPageIndex = viewModel.getCurrentPageIndex()
    val medicineInput by viewModel.medicineInput.collectAsState()
    val medicineInfo by viewModel.medicineInfo.collectAsState()
    val searchStatus by viewModel.searchStatus.collectAsState()
    val initStatus = viewModel.initStatus
    val selectedMedicine = viewModel.selectedMedicine
    val selectedIndex = viewModel.selectedIndex
    val currentUsedIndex = viewModel.currentUsedIndex
    val selectedDay = viewModel.selectedDays
    val selectedTime = viewModel.selectedTimes
    val startDate = viewModel.scheduleStartDate
    val endDate = viewModel.scheduleEndDate
    var showEndDatePicker by remember { mutableStateOf(true) }
    LaunchedEffect(memberId) {
        if (memberId != null) {
            viewModel.setMemberId(memberId)
            viewModel.getUsingCabinetIndex(memberId)
        }
    }

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
            when (currentPage) {
                schedulePages[0] -> {
                    CustomTextField(
                        state = true,
                        hint = "의약품명 검색",
                        value = medicineInput,
                        onValueChange = viewModel::updateInput,
                        trailIcon = R.drawable.ic_search,
                        onClickIcon = {
                            if (memberId != null) {
                                viewModel.getMedicineInfo(memberId, medicineInput)
                            }
                        },
                        enabled = medicineInput.isNotEmpty()
                    )
                    if(searchStatus) {
                        Column(
                            modifier = Modifier.padding(top = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            CustomLottieView(lottieAnim = R.raw.search_dose)
                            Text(
                                modifier = Modifier.padding(bottom = 4.dp),
                                text = "약품과 부작용을 조회하고 있어요",
                                color = Gray90,
                                style = PillinTimeTheme.typography.caption1Bold
                            )
                            Text(
                                text = "의약품의 종류에 따라 최대 20초 정도 걸릴 수 있어요.\n잠시만 기다려주세요...",
                                color = Gray90,
                                style = PillinTimeTheme.typography.caption1Regular,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                    // when there are results
                    if (medicineInfo.isNotEmpty() && !searchStatus) {
                        MedicineSearchResult(
                            modifier = Modifier.fadeInEffect(),
                            medicineList = medicineInfo,
                            selectedMedicine = selectedMedicine.value,
                            onMedicineClick = { medicine ->
                                viewModel.selectMedicine(medicine)
                                Log.d(
                                    "selectedMedicine",
                                    "${medicine.medicineName} ${viewModel.selectedMedicine.value?.medicineName}"
                                )
                            },
                        )
                    }
                    // when there are no results
                    else if (medicineInfo.isEmpty() && !searchStatus && initStatus.value) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(.8f),
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
                    // when initial status
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
                            text = "복용하는 요일을 선택해주세요",
                            color = Gray70,
                            style = PillinTimeTheme.typography.body1Medium
                        )
                        CustomWeekCalendar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            selectedDays = selectedDay,
                            isSelectable = true,
                            onDaySelected =
                            if (currentPageIndex == 1) { dayIndex ->
                                viewModel.selectDays(dayIndex)
                            } else {
                                {}
                            }
                        )
                    }
                    if (selectedDay.isNotEmpty() && currentPageIndex == 2) {
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .fadeInEffect()
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 36.dp, bottom = 8.dp),
                                text = "복용하는 시간대를 설정해주세요",
                                color = Gray70,
                                style = PillinTimeTheme.typography.body1Medium
                            )
                            ScheduleTimeButton(
                                selectedTimes = selectedTime,
                                onTimeSelected = { timeIndex ->
                                    viewModel.selectTimes(timeIndex)
                                }
                            )
                        }
                    }
                }

                schedulePages[3] -> {
                    Column {
                        Text(
                            modifier = Modifier.padding(bottom = 8.dp),
                            text = "복용 시작일",
                            color = Gray70,
                            style = PillinTimeTheme.typography.body1Medium
                        )
                        ScheduleDatePicker(startDate)
                        Spacer(modifier = Modifier.height(33.dp))
                        if (showEndDatePicker) {
                            Text(
                                modifier = Modifier.padding(bottom = 8.dp),
                                text = "복용 종료일",
                                color = Gray70,
                                style = PillinTimeTheme.typography.body1Medium
                            )
                            ScheduleDatePicker(endDate)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if(startDate.value > endDate.value) {
                                Text(
                                    modifier = Modifier.padding(start = 12.dp),
                                    text = "날짜를 다시 확인해주세요",
                                    color = Error60,
                                    style = PillinTimeTheme.typography.body2Regular
                                )
                                Spacer(modifier = Modifier.weight(1f))
                            }
                            Text(
                                text = "종료일 없음",
                                color = Gray70,
                                style = PillinTimeTheme.typography.body2Regular
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Switch(
                                checked = showEndDatePicker,
                                onCheckedChange = {
                                    showEndDatePicker = !showEndDatePicker
                                    endDate.value = "2099년 12월 31일"
                                },
                                colors = SwitchDefaults.colors().copy(
                                    checkedThumbColor = Primary60,
                                    checkedTrackColor = Gray20,
                                    checkedBorderColor = Color.Transparent,
                                    uncheckedThumbColor = Gray40,
                                    uncheckedTrackColor = Gray20,
                                    uncheckedBorderColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
                schedulePages[4] -> {
                    val indexColors =
                        listOf(Error60, Warning60, Success60, Primary40, Color(0xFF7D5DD9))
                    val colorToIndex = indexColors.withIndex().associate { it.value to it.index }
                    var selectColorIndex by remember { mutableIntStateOf(-1) }
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            indexColors.forEachIndexed { index, color ->
                                Box(
                                    modifier = Modifier
                                        .border(
                                            width = 1.dp,
                                            color = if (selectColorIndex == colorToIndex[color]) Gray90 else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .padding(4.dp)
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .clickable(
                                            onClick = {
                                                selectColorIndex =
                                                    colorToIndex[color] ?: selectColorIndex
                                                selectedIndex.intValue = index + 1
                                            },
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        )

                                )
                            }
                        }
                        if(currentUsedIndex.contains(selectedIndex.intValue)) {
                            Text(
                                modifier = Modifier.padding(top = 8.dp),
                                text = "해당 칸은 이미 사용중입니다.\n다른 칸을 선택하거나, 복용 계획을 삭제해주세요.",
                                color = Error60,
                                style = PillinTimeTheme.typography.body2Medium
                            )
                        }
                    }

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
                onClick = {
                    if (currentPageIndex == 4) {
                        memberId?.let { viewModel.postDoseSchedule(it, navController) }
                    } else {
                        viewModel.nextPage()
                    }
                }
            )
        }
    )
}