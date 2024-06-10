package com.whdaud.pillinTimeAndroid.presentation.schedule.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whdaud.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.whdaud.pillinTimeAndroid.ui.theme.Gray90
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.util.calendarTitle
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.Locale

@Composable
fun WeeklyCalendar(
    modifier: Modifier = Modifier,
    onClickDate: (LocalDate) -> Unit
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var currentPage by remember { mutableIntStateOf(0) }
    var currentYearMonth by remember { mutableStateOf(YearMonth.from(selectedDate)) }

    fun updateYearMonth(pageOffset: Int) {
        val referenceDate = LocalDate.now().plusWeeks(pageOffset.toLong())
        val startOfWeek = referenceDate.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1)
        val weekList = (0..6).map { startOfWeek.plusDays(it.toLong()) }
        val firstDayOfWeek = weekList.first()
        currentYearMonth = YearMonth.from(firstDayOfWeek)
    }
    fun moveToToday() {
        selectedDate = LocalDate.now()
        currentPage = 0
        currentYearMonth = YearMonth.from(LocalDate.now())
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = BasicPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        ) {

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = calendarTitle(currentYearMonth.year, currentYearMonth.monthValue),
                color = Gray90,
                style = PillinTimeTheme.typography.body2Bold,
            )
            TodayButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClickTodayButton = {
                    moveToToday()
                }
            )
        }
        val startOfWeek = LocalDate.now().with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1)
            .plusWeeks(currentPage.toLong())
        val weekList = (0..6).map { startOfWeek.plusDays(it.toLong()) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.clickable(
                    onClick = {
                        currentPage -= 1
                        updateYearMonth(currentPage)
                    },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.Unspecified
            )
            LazyRow(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items(weekList) { date ->
                    WeeklyCalendarItem(
                        date = date,
                        selectedDate = selectedDate,
                        onClickDate = {
                            selectedDate = it
                            onClickDate(it)
                        }
                    )
                }
            }
            Icon(
                modifier = Modifier.clickable(
                    onClick = {
                        currentPage += 1
                        updateYearMonth(currentPage)
                    },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}