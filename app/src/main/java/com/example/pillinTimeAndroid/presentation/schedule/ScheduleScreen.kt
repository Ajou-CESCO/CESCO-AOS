package com.example.pillinTimeAndroid.presentation.schedule

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.presentation.Dimens
import com.example.pillinTimeAndroid.presentation.common.CustomWeekCalendar
import com.example.pillinTimeAndroid.presentation.home.components.ClientListBar
import com.example.pillinTimeAndroid.presentation.schedule.components.ScheduleAddButton
import com.example.pillinTimeAndroid.presentation.schedule.components.ScheduleCard
import com.example.pillinTimeAndroid.ui.theme.Gray5

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel,
    navController: NavController
) {
    val userDetails by viewModel.userDetails.collectAsState()
    val users = viewModel.userList
    val pagerState = rememberPagerState(pageCount = { users.size })
    var selectedUserIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            if (userDetails?.isManager == true) {
                Column() {
                    ClientListBar(
                        users,
                        selectedIndex = selectedUserIndex,
                        onProfileSelected = { index ->
                            selectedUserIndex = index
                        }
                    )
                    CustomWeekCalendar(
                        modifier = Modifier.background(Gray5),
                        isSelectable = false,
                        onDaySelected = {}
                    )
                }
            }

        },
        content = { innerPadding ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) { page ->
                Box (
                    modifier = Modifier
                ){
                    ScheduleDetailScreen()
                    ScheduleAddButton(
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 12.dp, end = 16.dp),
                    ) {
                        navController.navigate("scheduleAddScreen")
                    }
                }

            }
        }
    )

    LaunchedEffect(selectedUserIndex) {
        pagerState.animateScrollToPage(selectedUserIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedUserIndex = pagerState.currentPage
    }
}

@Composable
fun ScheduleDetailScreen() {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray5)
            .verticalScroll(scrollState)
            .padding(horizontal = Dimens.BasicPadding),
        contentAlignment = Alignment.Center,

        ) {
        Spacer(modifier = Modifier.height(20.dp))
        Column() {
            ScheduleCard(state = 0)
            ScheduleCard(state = 1)
            ScheduleCard(state = 2)
        }

    }
}