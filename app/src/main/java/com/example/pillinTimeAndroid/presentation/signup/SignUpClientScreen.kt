package com.example.pillinTimeAndroid.presentation.signup

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.presentation.Dimens.BasicHeight
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.presentation.common.GeneralScreen
import com.example.pillinTimeAndroid.presentation.signup.components.ManagerRequestList
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpClientScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController,
) {
    val managerRequest by viewModel.managerRequest.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val title = if(managerRequest.isNotEmpty()) "보호자들이\n${userName}님을 기다리고 있어요" else "${userName}님을 케어할 수 있는\n보호자를 기다리고 있어요"
    val subtitle = if(managerRequest.isNotEmpty()) "단 한 명의 보호자만 선택할 수 있어요" else "케어 신청이 올 때까지 기다려 주세요..."
    val state = rememberPullToRefreshState()
    if (state.isRefreshing) {
        LaunchedEffect(true)  {
            viewModel.getManagerRequest()
            delay(1500)
            state.endRefresh()
        }
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
            }
        },
        button = {
            if (managerRequest.isEmpty()) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(BasicHeight),
                    onClick = { state.startRefresh() }
                ) {
                    Icon(Icons.Filled.Refresh, "Trigger Refresh")
                }
            }
        }
    )
}