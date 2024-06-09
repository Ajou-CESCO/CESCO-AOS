package com.whdaud.pillinTimeAndroid.presentation.schedule

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whdaud.pillinTimeAndroid.R
import com.whdaud.pillinTimeAndroid.presentation.common.ClientListBar
import com.whdaud.pillinTimeAndroid.presentation.common.CustomLottieView
import com.whdaud.pillinTimeAndroid.presentation.common.CustomSnackBar
import com.whdaud.pillinTimeAndroid.presentation.common.ManagerEmptyView
import com.whdaud.pillinTimeAndroid.presentation.main.MainViewModel
import com.whdaud.pillinTimeAndroid.presentation.schedule.calendar.WeeklyCalendar
import com.whdaud.pillinTimeAndroid.presentation.schedule.components.ScheduleAddButton
import com.whdaud.pillinTimeAndroid.presentation.schedule.components.ScheduleDetailPage
import com.whdaud.pillinTimeAndroid.util.getWeeksOfMonth
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
    var selectedUserIndex by remember { mutableIntStateOf(0) }

    val notificationResult by viewModel.notificationResult.collectAsState("")
    val snackbarHostState = remember { SnackbarHostState() }

    val selectedDate by viewModel.selectedDate.collectAsState()
    val totalWeeks =
        getWeeksOfMonth(selectedDate.year, selectedDate.monthValue, selectedDate.month.maxLength())
    val selectedWeeks =
        getWeeksOfMonth(selectedDate.year, selectedDate.monthValue, selectedDate.dayOfMonth)
    var isLoading by remember { mutableStateOf(true) }

    val calendarPagerState =
        rememberPagerState(pageCount = { totalWeeks }, initialPage = selectedWeeks - 1)
    var onClickedTodayButton by remember { mutableStateOf(false) }
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var showCalendarBottomSheet by remember { mutableStateOf(false) }


    // when manager, memberId = current profile index memberId, else memberId = userDetails.memberId
    val memberId = if (userDetails?.isManager == true) {
        if (relationInfoList.isNotEmpty()) {
            relationInfoList[selectedUserIndex].memberId
        } else {
            null
        }
    } else {
        userDetails?.memberId
    }

    val buttonState = when (userDetails?.isManager) {
        false -> userDetails?.cabinetId != 0
        relationInfoList.isNotEmpty() -> relationInfoList[selectedUserIndex].cabinetId != 0
        else -> false
    }

    val isButtonVisible: Boolean = when {
        // manager
        (userDetails?.isManager == true && relationInfoList.isEmpty()) -> false
        // client
        else -> true
    }

    val pagerState = rememberPagerState(
        pageCount = {
            if (userDetails?.isManager == true) {
                relationUserNames.size
            } else 1
        }
    )
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CustomLottieView(
                modifier = Modifier.size(200.dp),
                lottieAnim = R.raw.dots_loading
            )
        }
    } else {
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
                            scope.launch {
                                pagerState.scrollToPage(selectedUserIndex)
                            }
                        }
                    )
                }

                if (userDetails?.isManager == true && relationInfoList.isEmpty()) {
                    ManagerEmptyView(navController)
                } else {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ScheduleDetailPage(
                            modifier = Modifier.zIndex(-1f),
                            isManager = userDetails?.isManager == true,
                            onPullRefresh = {
                                scope.launch {
                                    isRefreshing = true
                                    mainViewModel.getInitData()
                                    if (memberId != null) {
                                        mainViewModel.getUserDoseLog(memberId)
                                        mainViewModel.getRelationship()
                                    }
                                    delay(1000)
                                    isRefreshing = false
                                }
                            },
                            isRefreshing = isRefreshing,
                            userDoseLog = userDoseLog,
                            onPokeClick = {
                                if (memberId != null) {
                                    viewModel.postFcmPushNotification(
                                        memberId,
                                        relationInfoList[selectedUserIndex].memberName
                                    )
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = notificationResult
                                        )
                                    }
                                }
                            },
                            // calendar content
                            calendar = {
                                WeeklyCalendar(
                                    pagerState = calendarPagerState,
                                    onClickDate = { clickedDate ->
                                        viewModel.updateDate(clickedDate)
                                    },
                                    onClickedTodayButton = {
                                        viewModel.setDateToday()
                                        onClickedTodayButton = true
                                        scope.launch {
                                            calendarPagerState.scrollToPage(selectedWeeks - 1)
                                        }
                                    },
                                    modifier = Modifier.weight(1f),
                                    selectedDate = selectedDate
                                )
                            }
                        )
                    }
                }
            }

            if (isButtonVisible) {
                ScheduleAddButton(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 12.dp, end = 16.dp),
                    buttonState = buttonState,
                    onClick = {
                        Log.e("scheduleAddScreen", "On Navigate With $memberId")
                        navController.navigate("scheduleAddScreen/${memberId}")
                    }
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxSize()
                    .zIndex(1f)
            ) {
                CustomSnackBar(
                    snackbarHostState = snackbarHostState,
                    message = notificationResult
                )
            }
        }
    }
    LaunchedEffect(userDetails) { if (userDetails != null) { isLoading = false } }
    LaunchedEffect(memberId) { memberId?.let { mainViewModel.getUserDoseLog(it) } }
    LaunchedEffect(true) { userDetails?.memberId?.let { mainViewModel.getUserDoseLog(it) } }
    LaunchedEffect(selectedUserIndex) { pagerState.scrollToPage(selectedUserIndex) }
    LaunchedEffect(pagerState.currentPage) { selectedUserIndex = pagerState.currentPage }
    LaunchedEffect(userDetails?.isManager) { mainViewModel.getRelationship() }
}
