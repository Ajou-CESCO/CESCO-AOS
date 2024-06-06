package com.example.pillinTimeAndroid.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.presentation.main.MainViewModel
import com.example.pillinTimeAndroid.presentation.mypage.components.MainMenu
import com.example.pillinTimeAndroid.presentation.mypage.components.SubMenu
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary40
import com.example.pillinTimeAndroid.ui.theme.White

@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val userDetails by mainViewModel.userDetails.collectAsState()
    val relationInfoList by mainViewModel.relationInfoList.collectAsState()

    val role = if (userDetails?.isManager == true) "보호자" else "피보호자"
    val name = userDetails?.name
    val context = LocalContext.current

    val navigateToScreen by viewModel.navigateToScreen.collectAsState()

    if (navigateToScreen != null) {
        navController.navigate(navigateToScreen!!) {
            launchSingleTop = true
            restoreState = true
        }
        viewModel.clearNavigation()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray5)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(horizontal = BasicPadding)
                .padding(bottom = BasicPadding)
        ) {
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = role,
                color = Primary40,
                style = PillinTimeTheme.typography.body1Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${name}님, 안녕하세요!",
                color = Gray90,
                style = PillinTimeTheme.typography.logo2Medium
            )
            Spacer(modifier = Modifier.height(35.dp))
            SubMenu(
                userDetails = userDetails,
                onItemClick = { destination ->
                    viewModel.navigateTo(destination)
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        MainMenu(
            isManager = userDetails?.isManager == true,
            onItemClick = { destination ->
                viewModel.navigateTo(destination)
            },
            onLogoutClick = { viewModel.signOut(navController, context) }
        )
    }
    LaunchedEffect(relationInfoList, userDetails) {
        if (relationInfoList.isEmpty() && userDetails?.isManager == false) {
            navController.navigate("signupClientScreen") {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }
}