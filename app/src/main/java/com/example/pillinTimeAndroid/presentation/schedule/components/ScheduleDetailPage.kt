package com.example.pillinTimeAndroid.presentation.schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.presentation.common.CustomWeekCalendar
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.util.PullToRefreshLazyColumn

@Composable
fun ScheduleDetailPage(
    modifier: Modifier,
    isManager: Boolean,
    userDoseLog: List<ScheduleLogDTO>,
    onPullRefresh: () -> Unit,
    isRefreshing: Boolean,
) {
    val groupedDoseLogs = userDoseLog.groupBy { it.takenStatus }
    val (backgroundColor, height) = if(!isManager) Pair(White, 54.dp) else Pair(Gray5, 64.dp)
    PullToRefreshLazyColumn(
        modifier = modifier,
        onPullRefresh = onPullRefresh,
        isRefreshing = isRefreshing,
        content = {
            Column(
                modifier = Modifier
                    .padding(vertical = 15.dp),
            ) {
                CustomWeekCalendar(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .background(backgroundColor)
                        .height(height),
                    isSelectable = false,
                    onDaySelected = {}
                )
                Column (
                    modifier = Modifier.padding(horizontal = BasicPadding)
                ) {
                    ScheduleCard(status = 0, doseLog = groupedDoseLogs[0] ?: emptyList())
                    ScheduleCard(status = 1, doseLog = groupedDoseLogs[1] ?: emptyList())
                    ScheduleCard(status = 2, doseLog = groupedDoseLogs[2] ?: emptyList())
                }

            }
        }
    )
}