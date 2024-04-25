package com.example.pillinTimeAndroid.presentation.btmnavigator

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.btmnavigator.component.BottomNavigationBar
import com.example.pillinTimeAndroid.presentation.btmnavigator.component.BottomNavigationItem
import com.example.pillinTimeAndroid.presentation.home.HomeScreen
import com.example.pillinTimeAndroid.presentation.home.HomeViewModel
import com.example.pillinTimeAndroid.presentation.mypage.MyPageScreen
import com.example.pillinTimeAndroid.presentation.nvgraph.Route
import com.example.pillinTimeAndroid.presentation.schedule.ScheduleScreen
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme

@Composable
fun BottomNavigator() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_tab_schedule),
            BottomNavigationItem(icon = R.drawable.ic_tab_home),
            BottomNavigationItem(icon = R.drawable.ic_tab_mypage),
        )
    }
    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(1)
    }

    selectedItem = when (backstackState?.destination?.route) {
        Route.ScheduleScreen.route -> 0
        Route.HomeScreen.route -> 1
        Route.MyPageScreen.route -> 2
        else -> 0
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                items = bottomNavigationItems,
                selected = selectedItem,
                onItemClick = { index -> navigateTab(navController, index) }
            )
        }
    ) {
        Crossfade(targetState = selectedItem) { selectedIndex ->
            NavHost(
                navController = navController,
                startDestination = Route.HomeScreen.route,
                modifier = Modifier.padding(bottom = it.calculateBottomPadding())
            ) {
                composable(route = Route.HomeScreen.route) {
                    if (selectedIndex == 1) HomeScreen()
                }
                composable(route = Route.ScheduleScreen.route) {
                    if (selectedIndex == 0) ScheduleScreen()
                }
                composable(route = Route.MyPageScreen.route) {
                    if (selectedIndex == 2) MyPageScreen()
                }
            }
        }
    }
}

fun navigateTab(navController: NavController, index: Int) {
    val route = when (index) {
        0 -> Route.ScheduleScreen.route
        1 -> Route.HomeScreen.route
        2 -> Route.MyPageScreen.route
        else -> return
    }
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) { saveState = true }
            restoreState = true
            launchSingleTop = true
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun BottomNavigatorPreview() {
    PillinTimeAndroidTheme {
        BottomNavigator()
    }
}
