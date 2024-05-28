package com.example.pillinTimeAndroid.presentation.signup

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.Dimens.BasicHeight
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.presentation.common.GeneralScreen
import com.example.pillinTimeAndroid.presentation.signup.components.ManagerRequestList
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpClientScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController,
) {
    val managerRequest by viewModel.managerRequest.collectAsState()
    val userName by viewModel.userName.collectAsState()
    Log.e("username", "$userName")
    val title =
        if (managerRequest.isNotEmpty()) "보호자들이\n${userName}님을 기다리고 있어요" else "${userName}님을 케어할 수 있는\n보호자를 기다리고 있어요"
    val subtitle =
        if (managerRequest.isNotEmpty()) "단 한 명의 보호자만 선택할 수 있어요" else "케어 신청이 올 때까지 기다려 주세요..."
    val state = rememberPullToRefreshState()
    if (state.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.getManagerRequest()
            delay(1500)
            state.endRefresh()
        }
    }
    LaunchedEffect(true) {
        viewModel.getManagerRequest()
        delay(1500)
    }
    GeneralScreen(
        topBar = {
            CustomTopBar()
        },
        title = title,
        subtitle = subtitle,
        content = {
            if (managerRequest.isNotEmpty()) {
                ManagerRequestList(
                    managers = managerRequest,
                    onConfirm = { requestId ->
                        viewModel.acceptManagerRequest(requestId, navController)
                    }
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(120.dp))
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
            }
        },
        button = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(BasicHeight),
                onClick = { state.startRefresh() },
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = Primary60
                ),
                shape = shapes.small
            ) {
                Text(
                    text = "새로고침",
                    color = White,
                    style = PillinTimeTheme.typography.body1Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Filled.Refresh,
                    "Trigger Refresh",
                    tint = White
                )
            }
        }
    )
}