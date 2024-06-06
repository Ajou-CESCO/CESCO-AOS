package com.example.pillinTimeAndroid.presentation.schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.presentation.common.CustomWeekCalendar
import com.example.pillinTimeAndroid.ui.theme.Gray10
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes
import com.example.pillinTimeAndroid.util.PullToRefreshLazyColumn

@Composable
fun ScheduleDetailPage(
    modifier: Modifier,
    isManager: Boolean,
    userDoseLog: List<ScheduleLogDTO>,
    onPullRefresh: () -> Unit,
    isRefreshing: Boolean,
    onPokeClick: () -> Unit = {}
) {
    val groupedDoseLogs = userDoseLog.groupBy { it.takenStatus }
    val (backgroundColor, height) = if(!isManager) Pair(White, 54.dp) else Pair(Gray5, 64.dp)
    PullToRefreshLazyColumn(
        modifier = modifier,
        onPullRefresh = onPullRefresh,
        isRefreshing = isRefreshing,
        content = {
            Column {
                CustomWeekCalendar(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .background(backgroundColor)
                        .height(height),
                    isSelectable = false,
                    onDaySelected = {}
                )
                Box {
                    if(!isManager) {
                        Button(
                            modifier = Modifier
                                .padding(end = 24.dp)
                                .align(Alignment.TopEnd)
                                .border(1.dp, Gray10, shapes.small)
                                .zIndex(1f),
                            onClick = onPokeClick,
                            shape = shapes.small,
                            colors = ButtonDefaults.buttonColors().copy(
                                containerColor = White
                            )
                        ) {
                            Icon(
                                modifier = Modifier.padding(end = 4.dp),
                                painter = painterResource(id = R.drawable.ic_poke),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            Text(text = "찌르기", color = Gray70, style = PillinTimeTheme.typography.body2Medium)
                        }
                    }
                    Column (
                        modifier = Modifier.padding(horizontal = BasicPadding)
                    ) {
                        ScheduleCard(status = 0, doseLog = groupedDoseLogs[0] ?: emptyList())
                        ScheduleCard(status = 1, doseLog = groupedDoseLogs[1] ?: emptyList())
                        ScheduleCard(status = 2, doseLog = groupedDoseLogs[2] ?: emptyList())
                    }
                }
            }
        }
    )
}