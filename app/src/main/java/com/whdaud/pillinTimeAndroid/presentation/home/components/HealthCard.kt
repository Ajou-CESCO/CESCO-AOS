package com.whdaud.pillinTimeAndroid.presentation.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.HealthStatDTO
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.ui.theme.Primary40
import com.whdaud.pillinTimeAndroid.ui.theme.Primary60
import com.whdaud.pillinTimeAndroid.ui.theme.White
import com.whdaud.pillinTimeAndroid.ui.theme.shapes
import com.google.gson.Gson
import java.time.Duration
import kotlin.math.roundToInt

@Composable
fun HealthCard(
    healthData: HealthStatDTO?,
    onCardClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = shapes.small)
            .background(Primary60)
            .padding(16.dp)
            .clickable(
                onClick = {
                    healthData?.let {
                        val healthDataJson = Gson().toJson(it)
                        onCardClick(healthDataJson)
                    }
                },
                enabled = healthData?.steps != 0,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .animateContentSize()
    ) {
        if (healthData?.steps == 0) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "건강 데이터가 없어요.",
                    color = White,
                    style = PillinTimeTheme.typography.logo4Medium
                )
                Text(
                    text = "건강 데이터 자료가 없어서, 통계를 내지 못했어요.",
                    color = White,
                    style = PillinTimeTheme.typography.body2Medium
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    ,
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "통계 보러가기",
                    color = White,
                    style = PillinTimeTheme.typography.body2Medium
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                HealthItem(healthData?.steps, "걸음 수", "보")
                HealthItem(healthData?.sleepTime, "수면", "시간")
                HealthItem(healthData?.heartRate, "심장박동수", "BPM")
                HealthItem(healthData?.calorie, "활동량", "kcal")
            }
        }
    }
}

@Composable
fun HealthItem(
    healthData: Any?,
    title: String,
    unit: String
) {
    val hd = healthData ?: "0"
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        Text(
            text = title,
            style = PillinTimeTheme.typography.caption1Medium,
            color = Primary40
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$hd $unit",
            style = PillinTimeTheme.typography.body1Bold,
            color = White
        )
    }
}

fun Duration.roundToNearestHour(): Int {
    val hours = this.toMinutes().toDouble() / 60
    return hours.roundToInt()
}