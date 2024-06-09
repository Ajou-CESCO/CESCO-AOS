package com.whdaud.pillinTimeAndroid.presentation.schedule.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.ui.theme.Primary60
import com.whdaud.pillinTimeAndroid.ui.theme.White
import com.whdaud.pillinTimeAndroid.ui.theme.shapes

@Composable
fun TodayButton(
    modifier: Modifier = Modifier,
    onClickTodayButton: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(shapes.small)
            .background(Primary60)
            .padding(6.dp)
            .clickable(
                onClick = onClickTodayButton,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "오늘",
            color = White,
            style = PillinTimeTheme.typography.body1Medium
        )
    }
}
