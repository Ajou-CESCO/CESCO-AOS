package com.example.pillinTimeAndroid.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.example.pillinTimeAndroid.domain.entity.HealthData
import com.example.pillinTimeAndroid.domain.entity.HomeUser
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes
import com.example.pillinTimeAndroid.util.PullToRefreshLazyColumn

@Composable
fun HomeDetailPage(
    modifier: Modifier,
    navController: NavController,
    userDetail: HomeUser?,
    userDoseLog: List<ScheduleLogDTO>,
    onPullRefresh: () -> Unit,
    isRefreshing: Boolean,
    healthData: HealthData? = null
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
                Spacer(modifier = Modifier.height(12.dp))
                HealthCard(
                    healthData = healthData,
                    onCardClick =
                    { navController.navigate("healthScreen/${userDetail?.name}/${userDetail?.memberId}/${userDetail?.isManager}")
                    }
                )
            }
        }
    )
}