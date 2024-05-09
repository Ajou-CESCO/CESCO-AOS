package com.example.pillinTimeAndroid.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.presentation.home.components.ClientListBar
import com.example.pillinTimeAndroid.presentation.home.components.DoseCard
import com.example.pillinTimeAndroid.presentation.home.components.HealthCard
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val userDetails by viewModel.userDetails.collectAsState()
    var selectedUser by remember { mutableStateOf<String?>(null) }
    val users = viewModel.userList
    val pagerState = rememberPagerState(pageCount = { users.size })
    var selectedUserIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            if (userDetails?.isManager == true) {
                ClientListBar(
                    users,
                    selectedIndex = selectedUserIndex,
                    onProfileSelected = { index ->
                        selectedUserIndex = index
                    }
                )
            }
        },
        content = { innerPadding ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) { page ->
                UserDetailScreen(userName = users[page])
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
fun UserDetailScreen(userName: String) {
    val scrollState = rememberScrollState()
    val textStyle = PillinTimeTheme.typography.logo3Medium.copy(color = Gray90)
    val greetingText = buildAnnotatedString {
        withStyle(style = textStyle.toSpanStyle().copy(color = Primary60)) {
            append(userName)
        }
        append("님,\n오늘 하루도 화이팅이에요!")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray5)
            .verticalScroll(scrollState)
            .padding(horizontal = BasicPadding),
    ) {
        Text(
            text = greetingText,
            style = textStyle
        )
        Spacer(modifier = Modifier.height(17.dp))
        Column(
            modifier = Modifier
                .clip(shapes.small)
                .background(White)
        ) {
            DoseCard()
        }
        Spacer(modifier = Modifier.height(23.dp))
        Column(
            modifier = Modifier
                .clip(shapes.small)
                .background(White)
        ) {
            HealthCard()
        }
    }
}