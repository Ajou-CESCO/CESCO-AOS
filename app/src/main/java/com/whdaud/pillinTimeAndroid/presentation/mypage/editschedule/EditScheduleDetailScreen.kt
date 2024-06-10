package com.whdaud.pillinTimeAndroid.presentation.mypage.editschedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whdaud.pillinTimeAndroid.data.remote.dto.ScheduleDTO
import com.whdaud.pillinTimeAndroid.presentation.common.ButtonColor
import com.whdaud.pillinTimeAndroid.presentation.common.ButtonSize
import com.whdaud.pillinTimeAndroid.presentation.common.CustomButton
import com.whdaud.pillinTimeAndroid.presentation.common.CustomWeekCalendar
import com.whdaud.pillinTimeAndroid.presentation.schedule.components.ScheduleTimeButton
import com.whdaud.pillinTimeAndroid.ui.theme.Gray70
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.util.fadeInEffect

@Composable
fun EditScheduleDetailScreen(
    schedule: ScheduleDTO?,
    viewModel: EditScheduleViewModel = hiltViewModel()
) {
    val userDetails by viewModel.userDetails.collectAsState()
    val selectedDay = viewModel.selectedDays
    val selectedTime = viewModel.selectedTimes

    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp)
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
            { dayIndex ->
                viewModel.selectDays(dayIndex)
            }
        )
        Column(
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
        Spacer(modifier = Modifier.height(440.dp))
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            filled = ButtonColor.FILLED,
            size = ButtonSize.MEDIUM,
            text = "수정하기",
            onClick = {
                schedule?.groupId?.let {
                    schedule.medicineId.let { it1 ->
                        schedule.medicineName.let { it2 ->
                            schedule.medicineAdverse.let { it3 ->
                                schedule.cabinetIndex.let { it4 ->
                                    selectedDay.let { it5 ->
                                        selectedTime.let { it6 ->
                                            schedule.startAt.let { it7 ->
                                                schedule.endAt.let { it8 ->
                                                    userDetails?.memberId?.let { it9 ->
                                                        ScheduleDTO(
                                                            groupId = it,
                                                            medicineId = it1,
                                                            medicineName = it2,
                                                            medicineAdverse = it3,
                                                            cabinetIndex = it4,
                                                            weekdayList = it5.map { it + 1 },
                                                            timeList = it6,
                                                            startAt = it7,
                                                            endAt = it8,
                                                            memberId = it9
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }?.let {
                    userDetails?.memberId?.let { it1 ->
                        viewModel.patchDoseSchedule(
                            scheduleDTO = it,
                            memberId = it1
                        )
                    }
                }
            }
        )
    }
}