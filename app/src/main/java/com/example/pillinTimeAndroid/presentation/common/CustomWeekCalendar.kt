package com.example.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes
import java.util.Calendar

@Composable
fun CustomWeekCalendar(
    modifier: Modifier,
    isSelectable: Boolean,
    selectedDays: List<Int> = emptyList(),
    onDaySelected: (Int) -> Unit
) {
    val days = listOf("일", "월", "화", "수", "목", "금", "토")
    val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1
    Row(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        days.forEachIndexed { index, day ->
            if (isSelectable) {
                val isSelected = selectedDays.contains(index)
                val dayBackground = if (isSelected) Primary60 else Color.Transparent
                val dayText = if (isSelected) White else Gray70
                CalendarDay(day, dayBackground, dayText) {
                    onDaySelected(index)
                }
            } else {
                val (dayBackground, dayText) = if (index == today) {
                    Pair(Primary60, White)
                } else {
                    Pair(Color.Transparent, Gray70)
                }
                CalendarDay(day, dayBackground, dayText) {}
            }
        }
    }
}

@Composable
fun CalendarDay(
    day: String,
    backgroundColor: Color,
    textColor: Color,
    onDayClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(shapes.small)
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .clickable(
                onClick = onDayClicked,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        Text(
            text = day,
            color = textColor,
            style = PillinTimeTheme.typography.body1Regular
        )
    }
}