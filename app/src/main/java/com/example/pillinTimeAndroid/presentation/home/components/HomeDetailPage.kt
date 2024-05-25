package com.example.pillinTimeAndroid.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.data.remote.dto.ScheduleLogDTO
import com.example.pillinTimeAndroid.domain.entity.User
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
    userDetail: User?,
    userDoseLog: List<ScheduleLogDTO>,
    onPullRefresh: () -> Unit,
    isRefreshing: Boolean,
) {
    val textStyle = PillinTimeTheme.typography.logo3Medium.copy(color = Gray90)
    val managerGreetingText = buildAnnotatedString {
        append("오늘 ")
        withStyle(style = textStyle.toSpanStyle().copy(color = Primary60)) {
            append(userDetail?.name.toString())
        }
        append("님의 ")
        withStyle(style = textStyle.toSpanStyle().copy(color = Primary60)) {
            append("약")
        }
        append("속시간은?")
    }
    val clientGreetingText = buildAnnotatedString {
        withStyle(style = textStyle.toSpanStyle().copy(color = Primary60)) {
            append(userDetail?.name.toString())
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
            Column (modifier = Modifier.padding(horizontal = BasicPadding)){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = greetingText,
                        style = textStyle,
                    )
                    if (userDetail?.isManager == false) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_alert_off),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                }
                Spacer(modifier = Modifier.height(17.dp))
                Column(
                    modifier = Modifier
                        .clip(shapes.small)
                        .background(White)
                ) {
                    userDetail?.cabinetId?.let {
                        HomeDoseCard(
                            cabinetId = it,
                            onRegisterClick = { navController.navigate("cabinetRegisterScreen") },
                            doseLog = userDoseLog
                        )
                    }
                }
                Spacer(modifier = Modifier.height(23.dp))
                HealthStatisticCard()
                Spacer(modifier = Modifier.height(12.dp))
                HealthCard()
            }
        }
    )
}