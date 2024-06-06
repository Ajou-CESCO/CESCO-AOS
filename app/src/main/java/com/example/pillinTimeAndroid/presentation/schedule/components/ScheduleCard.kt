package com.example.pillinTimeAndroid.presentation.schedule.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.example.pillinTimeAndroid.presentation.home.components.DoseItem
import com.example.pillinTimeAndroid.ui.theme.Error40
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary40
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun ScheduleCard(
    status: Int,
    doseLog: List<ScheduleLogDTO>,
) {
    val (pillColor, statusWord) = when (status) {
        0 -> Pair(Color.Unspecified, "예정")
        1 -> Pair(Primary40, "완료")
        2 -> Pair(Error40, "미완료")
        else -> Pair(Color.Unspecified, "예정")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(bottom = 16.dp)
            .animateContentSize()

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pill),
                contentDescription = "",
                tint = pillColor
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${statusWord}된 약속시간",
                color = Gray70,
                style = PillinTimeTheme.typography.body2Medium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (doseLog.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shapes.small)
                    .background(White)
                    .padding(45.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 2.dp),
                    text = "${statusWord}된 복약 계획이 없습니다.",
                    color = Gray90,
                    style = PillinTimeTheme.typography.body1Bold,
                )
                Text(
                    text = "복약 일정을 등록하고 알림을 받아보세요",
                    color = Gray90,
                    style = PillinTimeTheme.typography.caption1Medium,
                )
            }

        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shapes.small)
                    .background(White)
            ) {
                doseLog.forEach { log ->
                    DoseItem(doseLog = log)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
