package com.example.pillinTimeAndroid.presentation.schedule.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.Dimens.BasicHeight
import com.example.pillinTimeAndroid.ui.theme.Gray10
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.Gray50
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ScheduleTimeButton(
    selectedTimes: List<String> = emptyList(),
    onTimeSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var parentHeight by remember { mutableStateOf(0.dp) }

    Box(
        modifier = Modifier
            .onSizeChanged { newSize ->
                parentHeight = newSize.height.dp
            }
    ) {
        val height by animateDpAsState(targetValue = if (expanded) parentHeight else BasicHeight)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shapes.small)
                .background(Gray5)
                .height(height)
                .border(2.dp, Gray10, shapes.small)
                .padding(12.dp)
                .padding(top = 4.dp)
                .onSizeChanged { newSize ->
                    parentHeight = newSize.height.dp
                }
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { expanded = !expanded },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.ic_clock),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
                Text(
                    text = "시간 선택",
                    color = Gray70,
                    style = PillinTimeTheme.typography.headline5Medium
                )
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "",
                    tint = Gray70
                )
            }
            if (expanded) {
                TimeSelectGrid(
                    selectedTimes = selectedTimes,
                    onTimeSelected = onTimeSelected
                )
            }
        }
    }
}

@Composable
fun TimeSelectGrid(
    selectedTimes: List<String>,
    onTimeSelected: (String) -> Unit
) {
    val times = List(24 * 2) { index ->
        val hour = index / 2
        val minute = if (index % 2 == 0) "00" else "30"
        String.format("%02d:%s", hour, minute)
    }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(top = 16.dp),
    ) {
        times.chunked(3).forEach { rowTimes ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                rowTimes.forEach { time ->
                    TimeButton(
                        modifier = Modifier.weight(1f),
                        time = time,
                        isSelected = selectedTimes.contains(time),
                        onTimeToggle = onTimeSelected,
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun TimeButton(
    modifier: Modifier = Modifier,
    time: String,
    isSelected: Boolean,
    onTimeToggle: (String) -> Unit
) {
    Box(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .clip(shapes.medium)
            .background(if (isSelected) Primary60 else Gray5)
            .border(1.dp, Gray50, shapes.medium)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onTimeToggle(time)
            },
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp, vertical = 18.dp),
            text = time,
            color = if (isSelected) White else Gray50,
            style = PillinTimeTheme.typography.body1Medium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleDatePicker(
    selectedDate: MutableState<String>
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val localFormatter = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapes.small)
            .background(Gray5)
            .padding(17.dp)
            .clickable(
                onClick = { showDatePicker = true },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.ic_clock_filled),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = selectedDate.value.ifEmpty { localFormatter.format(Date()) },
            color = Gray90,
            style = PillinTimeTheme.typography.headline5Medium
        )
    }
    if (showDatePicker) {
        ScheduleDatePickerDialog(
            onDateSelected = { selectedDate.value = it },
            onDismiss = { showDatePicker = false }
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun ScheduleDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val remoteFormatter = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
    val selectedDate = datePickerState.selectedDateMillis?.let {
        remoteFormatter.format(Date(it))
    } ?: remoteFormatter.format(Date())

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                modifier = Modifier
                    .padding(end = 15.dp, bottom = 15.dp),
                onClick = {
                    onDateSelected(selectedDate)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = Primary60,
                    contentColor = White
                ),
                shape = shapes.small
            ) {
                Text(
                    text = "선택",
                    style = PillinTimeTheme.typography.body1Medium
                )
            }
        },
        dismissButton = {
            Button(
                modifier = Modifier
                    .padding(bottom = 15.dp),
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = Gray5,
                    contentColor = Gray50
                ),
                shape = shapes.small
            ) {
                Text(
                    text = "취소",
                    style = PillinTimeTheme.typography.body1Medium
                )
            }
        },
        colors = DatePickerDefaults.colors().copy(containerColor = White)
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            colors = DatePickerDefaults.colors().copy(
                containerColor = White,
                titleContentColor = Gray90,
                headlineContentColor = Gray90,
                weekdayContentColor = Gray90,
                navigationContentColor = Gray90,
                yearContentColor = Gray90,
                dayContentColor = Gray90,
                currentYearContentColor = Gray90,
                selectedYearContainerColor = Primary60,
                selectedYearContentColor = White,
                selectedDayContentColor = White,
                selectedDayContainerColor = Primary60,
                todayContentColor = Gray90,
                todayDateBorderColor = Primary60,
                dividerColor = Gray70
            )
        )
    }
}