package com.whdaud.pillinTimeAndroid.presentation.schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.whdaud.pillinTimeAndroid.R
import com.whdaud.pillinTimeAndroid.data.remote.dto.ScheduleLog
import com.whdaud.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.whdaud.pillinTimeAndroid.ui.theme.Gray10
import com.whdaud.pillinTimeAndroid.ui.theme.Gray70
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.ui.theme.White
import com.whdaud.pillinTimeAndroid.ui.theme.shapes
import com.whdaud.pillinTimeAndroid.util.PullToRefreshLazyColumn

@Composable
fun ScheduleDetailPage(
    modifier: Modifier,
    isManager: Boolean,
    userDoseLog: List<ScheduleLog>,
    onPullRefresh: () -> Unit,
    isRefreshing: Boolean,
    onPokeClick: () -> Unit = {},
    calendar: @Composable () -> Unit = {}
) {
    val groupedDoseLogs = userDoseLog.groupBy { it.takenStatus }

    PullToRefreshLazyColumn(
        modifier = modifier,
        onPullRefresh = onPullRefresh,
        isRefreshing = isRefreshing,
        content = {
            Box {
                if (isManager) {
                    Row(
                        modifier = Modifier
                            .padding(top = 128.dp, end = 24.dp)
                            .align(Alignment.TopEnd)
                            .clip(shapes.small)
                            .background(White)
                            .border(1.dp, Gray10, shapes.small)
                            .zIndex(1f)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .clickable{ onPokeClick() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 4.dp),
                            painter = painterResource(id = R.drawable.ic_poke),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Text(
                            text = "찌르기",
                            color = Gray70,
                            style = PillinTimeTheme.typography.body2Medium
                        )
                    }
                }
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    calendar()
                    Column(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .padding(horizontal = BasicPadding)
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