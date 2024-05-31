package com.example.pillinTimeAndroid.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.common.CustomLottieViewV2
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun HealthStatisticCard(
    onCardClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapes.small)
            .background(White)
            .padding(10.dp)
            .clickable(
                onClick = onCardClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CustomLottieViewV2(
            lottieAnim = R.raw.health_pie_chart,
            modifier = Modifier.size(56.dp)
        )
        Column {
            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = "오늘 많이 걸으셨나요?",
                color = Gray90,
                style = PillinTimeTheme.typography.logo4Extra
            )
            Text(
                text = "50대 평균보다 300보 덜 걸으셨어요.",
                color = Gray70,
                style = PillinTimeTheme.typography.caption1Regular
            )
        }
    }
}