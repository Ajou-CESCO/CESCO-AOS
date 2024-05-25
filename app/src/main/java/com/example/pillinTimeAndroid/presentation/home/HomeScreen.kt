package com.example.pillinTimeAndroid.presentation.home

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.presentation.common.ClientListBar
import com.example.pillinTimeAndroid.presentation.common.ManagerEmptyView
import com.example.pillinTimeAndroid.presentation.common.PermissionDialog
import com.example.pillinTimeAndroid.presentation.common.RationaleDialog
import com.example.pillinTimeAndroid.presentation.home.components.HomeDetailPage
import com.example.pillinTimeAndroid.presentation.main.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        RequestNotificationPermissionDialog()
    }

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

    val permissionsLauncher =
        rememberLauncherForActivityResult(viewModel.permissionsLauncher) {
            viewModel.fetchHealthData()
        }

    LaunchedEffect(true) {
        permissionsLauncher.launch(viewModel.permissions)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
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
            HomeDetailPage(
                modifier = Modifier.zIndex(-1f),
                navController = navController,
                userDetail = userDetails,
                onPullRefresh = {
                    scope.launch {
                        isRefreshing = true
                        userDetails?.memberId?.let { mainViewModel.getUserDoseLog(it) }
                        mainViewModel.getInitData()
                        delay(1000)
                        isRefreshing = false
                    }
                },
                isRefreshing = isRefreshing,
                userDoseLog = userDoseLog,
            )
        }
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

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) RationaleDialog()
        else PermissionDialog { permissionState.launchPermissionRequest() }
    }
}

