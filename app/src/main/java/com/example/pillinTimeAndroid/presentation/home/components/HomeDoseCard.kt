package com.example.pillinTimeAndroid.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.example.pillinTimeAndroid.ui.theme.Error60
import com.example.pillinTimeAndroid.ui.theme.Gray10
import com.example.pillinTimeAndroid.ui.theme.Gray30
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.Primary90
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun HomeDoseCard(
    cabinetId: Int,
    onRegisterClick: () -> Unit
) {
    if (cabinetId == 0) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray10)
                .clip(shapes.small),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
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
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shapes.small),
        ) {

        }
    }
}

@Composable
fun DoseItem(
    state: Int = 2,
    doseLog: ScheduleLogDTO
) {
    val (doseColor, doseStatus) = when (doseLog.takenStatus) {
        1 -> Pair(Primary60, "완료")
        2 -> Pair(Error60, "미완료")
        else -> Pair(Gray30, "예정")
    }
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
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.5.dp)
                .padding(start = 12.dp),
            text = doseLog.medicineName,
            style = PillinTimeTheme.typography.headline5Bold,
            color = Gray90
        )
        Text(
            modifier = Modifier
                .weight(.3f)
                .padding(end = 7.dp),
            textAlign = TextAlign.Center,
            text = doseLog.plannedAt,
            style = PillinTimeTheme.typography.body2Medium,
            color = Gray70
        )
        Text(
            modifier = Modifier
                .weight(.3f)
                .padding(end = 7.dp),
            textAlign = TextAlign.Center,
            text = doseStatus,
            style = PillinTimeTheme.typography.logo4Extra,
            color = doseColor
        )
    }
}