package com.example.pillinTimeAndroid.presentation.schedule

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.presentation.common.ClientListBar
import com.example.pillinTimeAndroid.presentation.common.ManagerEmptyView
import com.example.pillinTimeAndroid.presentation.main.MainViewModel
import com.example.pillinTimeAndroid.presentation.schedule.components.ScheduleAddButton
import com.example.pillinTimeAndroid.presentation.schedule.components.ScheduleDetailPage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val userDetails by mainViewModel.userDetails.collectAsState()
    val userDoseLog by mainViewModel.userDoseLog.collectAsState()
    val relationInfoList by mainViewModel.relationInfoList.collectAsState()
    val relationUserNames = relationInfoList.map { it.memberName }
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

    val users = viewModel.userList
    val pagerState = rememberPagerState(
        pageCount = {
            if (userDetails?.isManager == true) {
                users.size
            } else 1
        }
    )
    var selectedUserIndex by remember { mutableIntStateOf(0) }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            if (userDetails?.isManager == true) {
                ClientListBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(1f),
                    profiles = relationUserNames,
                    selectedIndex = selectedUserIndex,
                    onProfileSelected = { index ->
                        selectedUserIndex = index
                    }
                )
            }
            if(userDetails?.isManager == true && relationInfoList.isEmpty()) {
                ManagerEmptyView(navController)
            } else {
                ScheduleDetailPage(
                    modifier = Modifier.zIndex(-1f),
                    isManager = userDetails?.isManager == true,
                    onPullRefresh = {
                        scope.launch {
                            isRefreshing = true
                            mainViewModel.getInitData()
                            userDetails?.memberId?.let { mainViewModel.getUserDoseLog(it) }
                            delay(1000)
                            isRefreshing = false
                        }
                    },
                    isRefreshing = isRefreshing,
                    userDoseLog = userDoseLog
                )
            }
        }
        if(relationInfoList.isNotEmpty()) {
            ScheduleAddButton(
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 12.dp, end = 16.dp),
            ) {
                navController.navigate("scheduleAddScreen")
            }
        }
    }
    LaunchedEffect(userDetails) {
        userDetails?.let {
            mainViewModel.getUserDoseLog(it.memberId)
        }
    }
    LaunchedEffect(true) {
        userDetails?.memberId?.let { mainViewModel.getUserDoseLog(it) }
    }
    LaunchedEffect(selectedUserIndex) {
        pagerState.animateScrollToPage(selectedUserIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedUserIndex = pagerState.currentPage
    }
}