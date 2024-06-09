package com.whdaud.pillinTimeAndroid.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.whdaud.pillinTimeAndroid.data.remote.dto.ScheduleLog
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.HealthStatDTO
import com.whdaud.pillinTimeAndroid.domain.entity.HomeUser
import com.whdaud.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.whdaud.pillinTimeAndroid.ui.theme.Gray70
import com.whdaud.pillinTimeAndroid.ui.theme.Gray90
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.ui.theme.Primary60
import com.whdaud.pillinTimeAndroid.ui.theme.White
import com.whdaud.pillinTimeAndroid.ui.theme.shapes
import com.whdaud.pillinTimeAndroid.util.PullToRefreshLazyColumn

@Composable
fun HomeDetailPage(
    modifier: Modifier,
    navController: NavController,
    userDetail: HomeUser?,
    userDoseLog: List<ScheduleLog>,
    onPullRefresh: () -> Unit,
    onHealthRefresh: () -> Unit,
    isRefreshing: Boolean,
    healthData: HealthStatDTO? = null
) {
    val textStyle = PillinTimeTheme.typography.logo3Medium.copy(color = Gray90)
    val managerGreetingText = buildAnnotatedString {
        append("오늘 ")
        withStyle(style = textStyle.toSpanStyle().copy(color = Primary60)) {
            append(userDetail?.name)
        }
        append("님의 ")
        withStyle(style = textStyle.toSpanStyle().copy(color = Primary60)) {
            append("약")
        }
        append("속시간은?")
    }
    val clientGreetingText = buildAnnotatedString {
        withStyle(style = textStyle.toSpanStyle().copy(color = Primary60)) {
            append(userDetail?.name)
        }
        append("님,\n오늘 하루도 화이팅이에요!")
    }
    val greetingText =
        if (userDetail?.isManager == true) managerGreetingText else clientGreetingText
    PullToRefreshLazyColumn(
        modifier = modifier,
        onPullRefresh = onPullRefresh,
        isRefreshing = isRefreshing,
        content = {
            Column(
                modifier = Modifier.padding(horizontal = BasicPadding, vertical = 15.dp)
            ) {
                Text(
                    text = greetingText,
                    style = textStyle,
                )
                Spacer(modifier = Modifier.height(17.dp))
                Column(
                    modifier = Modifier
                        .clip(shapes.small)
                        .background(White)
                ) {
                    userDetail?.cabinetId?.let {
                        HomeDoseCard(
                            cabinetId = it,
                            onRegisterClick = { navController.navigate("cabinetRegisterScreen/${userDetail.memberId}") },
                            doseLog = userDoseLog
                        )
                    }
                }
                Spacer(modifier = Modifier.height(23.dp))
                HealthCard(
                    healthData = healthData,
                    onCardClick = {healthDataJson ->
                        navController.navigate("healthScreen/${userDetail?.name}/${healthDataJson}")
                    }
                )
                if(userDetail?.isManager == false) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .clickable { onHealthRefresh() },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = null,
                            tint = Gray70
                        )
                        Text(
                            modifier = Modifier.padding(start = 4.dp),
                            text = "건강 정보 다시 불러오기",
                            color = Gray70,
                            style = PillinTimeTheme.typography.body2Medium
                        )
                    }
                }
            }
        }
    )
}