package com.whdaud.pillinTimeAndroid.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.whdaud.pillinTimeAndroid.R
import com.whdaud.pillinTimeAndroid.data.remote.dto.ScheduleLog
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.HealthStatDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.RelationReqResponse
import com.whdaud.pillinTimeAndroid.domain.entity.HomeUser
import com.whdaud.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.whdaud.pillinTimeAndroid.presentation.signup.components.ManagerRequestList
import com.whdaud.pillinTimeAndroid.ui.theme.Gray70
import com.whdaud.pillinTimeAndroid.ui.theme.Gray90
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.ui.theme.Primary60
import com.whdaud.pillinTimeAndroid.ui.theme.White
import com.whdaud.pillinTimeAndroid.ui.theme.shapes
import com.whdaud.pillinTimeAndroid.util.PullToRefreshLazyColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDetailPage(
    modifier: Modifier,
    navController: NavController,
    userDetail: HomeUser?,
    userDoseLog: List<ScheduleLog>,
    onPullRefresh: () -> Unit,
    onHealthRefresh: () -> Unit,
    isRefreshing: Boolean,
    healthData: HealthStatDTO? = null,
    managerRequest: List<RelationReqResponse> = emptyList(),
    onConfirm: (Int, String?) -> Unit
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
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    if(showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = BasicPadding),
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = White
        ) {
            Column(
                modifier = Modifier.padding(horizontal = BasicPadding)
            ) {
                val title =
                    if (managerRequest.isNotEmpty()) "보호자들이\n${userDetail?.name}님을 기다리고 있어요" else "${userDetail?.name}님을 케어할 수 있는\n보호자를 기다리고 있어요"
                val subtitle =
                    if (managerRequest.isNotEmpty()) "단 한 명의 보호자만 선택할 수 있어요" else "케어 신청이 올 때까지 기다려 주세요..."
                Text(
                    text =title,
                    color = Gray90,
                    style = PillinTimeTheme.typography.logo2Extra
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp, bottom = 36.dp),
                    text =subtitle,
                    color = Gray90,
                    style = PillinTimeTheme.typography.logo4Medium
                )
                if(managerRequest.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_pill_not_found),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Text(
                            text = "요청을 다시 확인해주세요",
                            color = Gray90,
                            style = PillinTimeTheme.typography.caption1Bold
                        )
                        Text(
                            text = "보호관계 요청 결과가 없습니다.",
                            color = Gray90,
                            style = PillinTimeTheme.typography.caption1Regular
                        )
                    }
                } else {
                    ManagerRequestList(
                        managers = managerRequest,
                        onConfirm = { requestId, managerName ->
                            onConfirm(requestId, managerName)
                        }
                    )
                }
            }
        }
    }
    PullToRefreshLazyColumn(
        modifier = modifier,
        onPullRefresh = onPullRefresh,
        isRefreshing = isRefreshing,
        content = {
            Column(
                modifier = Modifier.padding(horizontal = BasicPadding, vertical = 15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = greetingText,
                        style = textStyle,
                    )
                    if(userDetail?.isManager == false) {
                        Icon(
                            modifier = Modifier.clickable(
                                onClick = { showBottomSheet = true }
                            ),
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