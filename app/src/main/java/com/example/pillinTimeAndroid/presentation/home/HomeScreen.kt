package com.example.pillinTimeAndroid.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.presentation.home.components.ClientListBar
import com.example.pillinTimeAndroid.presentation.home.components.HomeDetailPage
import com.example.pillinTimeAndroid.presentation.main.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val userDetails by mainViewModel.userDetails.collectAsState()
    val userDoseLog by mainViewModel.userDoseLog.collectAsState()
    val relationInfoList by mainViewModel.relationInfoList.collectAsState()
    val relationUserNames = relationInfoList.map { it.memberName }
    var selectedUser by remember { mutableStateOf<String?>(null) }
//    val relationInfoList by mainViewModel.relationInfoList
//    val userDetails by viewModel.userDetails.collectAsState()
//    val userDetailsV2 = viewModel.userInfo
    var isRefreshing by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { relationUserNames.size })
    var selectedUserIndex by remember { mutableIntStateOf(0) }
    var isPageChanging by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (userDetails?.isManager == true) {
            ClientListBar(
                relationUserNames,
                selectedIndex = selectedUserIndex,
                onProfileSelected = { index ->
                    selectedUserIndex = index
                }
            )
        }
        HomeDetailPage(
            modifier = Modifier,
            navController = navController,
            userDetail = userDetails,
            onPullRefresh = {
                scope.launch {
                    isRefreshing = true
                    userDetails?.memberId?.let { mainViewModel.getUserDoseLog(it) }
                    delay(1000)
                    isRefreshing = false
                }
            },
            isRefreshing = isRefreshing,
            userDoseLog = userDoseLog
        )
    }
    LaunchedEffect(userDetails) {
        userDetails?.let {
            mainViewModel.getUserDoseLog(it.memberId)
        }
    }

    LaunchedEffect(selectedUserIndex) {
        pagerState.animateScrollToPage(selectedUserIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedUserIndex = pagerState.currentPage
    }
    LaunchedEffect(selectedUserIndex) {
        if (!isPageChanging) {
            isPageChanging = true
            pagerState.animateScrollToPage(selectedUserIndex)
            isPageChanging = false
        }
    }
}

