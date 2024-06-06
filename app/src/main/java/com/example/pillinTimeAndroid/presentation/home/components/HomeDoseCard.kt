package com.example.pillinTimeAndroid.presentation.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.example.pillinTimeAndroid.ui.theme.Error60
import com.example.pillinTimeAndroid.ui.theme.Gray10
import com.example.pillinTimeAndroid.ui.theme.Gray30
import com.example.pillinTimeAndroid.ui.theme.Gray60
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.Primary90
import com.example.pillinTimeAndroid.ui.theme.Purple60
import com.example.pillinTimeAndroid.ui.theme.Success60
import com.example.pillinTimeAndroid.ui.theme.Warning60
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeDoseCard(
    cabinetId: Int,
    onRegisterClick: () -> Unit,
    doseLog: List<ScheduleLogDTO>
) {
    val backgroundColor = if(cabinetId == 0 || doseLog.isEmpty()) Gray10 else White
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .animateContentSize()
            .clip(shapes.small),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (cabinetId == 0) {
            Text(
                modifier = Modifier.padding(top = 75.dp, bottom = 2.dp),
                text = "등록된 기기가 없어요",
                color = Gray90,
                style = PillinTimeTheme.typography.body1Bold
            )
            Text(
                modifier = Modifier.padding(bottom = 33.dp),
                text = "약통을 연동하여 복약 일정을 등록해보세요",
                color = Gray90,
                style = PillinTimeTheme.typography.caption1Medium
            )
            Button(
                modifier = Modifier
                    .padding(bottom = 17.dp)
                    .size(130.dp, 48.dp),
                onClick = onRegisterClick,
                shape = shapes.small,
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = White
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "기기 등록하기",
                    color = Primary90,
                    style = PillinTimeTheme.typography.body1Medium
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor)
                    .heightIn(max = 300.dp)
                    .verticalScroll(scrollState)
                    .clip(shapes.small) ,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if(doseLog.isNotEmpty()) {
                    doseLog.forEach { log ->
                        DoseItem(doseLog = log)
                    }
                } else {
                    Text(
                        modifier = Modifier.padding(top = 32.dp, bottom = 2.dp),
                        text = "오늘 등록된 복약 일정이 없어요",
                        color = Gray90,
                        style = PillinTimeTheme.typography.body1Bold
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 32.dp),
                        text = "복약 일정을 등록하고 알림을 받아보세요",
                        color = Gray90,
                        style = PillinTimeTheme.typography.caption1Medium
                    )
                }
            }
        }
    }
}

@Composable
fun DoseItem(
    doseLog: ScheduleLogDTO
) {
    val (doseColor, doseStatus) = when (doseLog.takenStatus) {
        1 -> Pair(Primary60, "완료")
        2 -> Pair(Error60, "미완료")
        else -> Pair(Gray30, "예정")
    }
    val indexColor = listOf(Error60, Warning60, Success60, Primary60, Purple60)
    val cabinetColor =
        if (doseLog.cabinetIndex in 1..5) indexColor[doseLog.cabinetIndex - 1]
        else Gray60 // index out of range 방지

    Row(
        modifier = Modifier
            .padding(
                vertical = 10.dp,
                horizontal = 8.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier
            .weight(.1f)
            .size(20.dp)) {
            drawCircle(
                color = cabinetColor,
                radius = size.minDimension / 2
            )
        }
        Text(
            modifier = Modifier
                .weight(.5f)
                .padding(vertical = 10.5.dp)
                .padding(start = 12.dp),
            text = doseLog.medicineName,
            style = PillinTimeTheme.typography.headline5Bold,
            color = Gray90,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.weight(.3f),
            textAlign = TextAlign.Center,
            text = convertToAmPm(doseLog.plannedAt.substring(0, 5)),
            style = PillinTimeTheme.typography.body2Medium,
            color = Gray70
        )
        Text(
            modifier = Modifier.weight(.2f),
            textAlign = TextAlign.Center,
            text = doseStatus,
            style = PillinTimeTheme.typography.logo4Extra,
            color = doseColor
        )
    }
}
fun convertToAmPm(time: String): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val parsedTime = LocalTime.parse(time, formatter)
    val hour = if (parsedTime.hour % 12 == 0) 12 else parsedTime.hour % 12
    val amPm = if (parsedTime.hour < 12) "오전" else "오후"
    val minutes = if (parsedTime.minute == 0) "" else "${parsedTime.minute.toString().padStart(2, '0')}분"
    return "$amPm ${hour}시 ${minutes}"
}