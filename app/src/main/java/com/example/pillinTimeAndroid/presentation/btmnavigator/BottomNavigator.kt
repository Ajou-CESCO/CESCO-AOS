package com.example.pillinTimeAndroid.presentation.btmnavigator

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.btmnavigator.component.BottomNavigationBar
import com.example.pillinTimeAndroid.presentation.btmnavigator.component.BottomNavigationItem
import com.example.pillinTimeAndroid.presentation.home.HomeScreen
import com.example.pillinTimeAndroid.presentation.mypage.MyPageScreen
import com.example.pillinTimeAndroid.presentation.mypage.cabinet.CabinetManageScreen
import com.example.pillinTimeAndroid.presentation.mypage.cabinet.CabinetRegisterScreen
import com.example.pillinTimeAndroid.presentation.mypage.editinfo.EditInfoScreen
import com.example.pillinTimeAndroid.presentation.mypage.withdrawal.WithdrawalScreen
import com.example.pillinTimeAndroid.presentation.nvgraph.Route
import com.example.pillinTimeAndroid.presentation.schedule.ScheduleAddScreen
import com.example.pillinTimeAndroid.presentation.schedule.ScheduleScreen
import com.example.pillinTimeAndroid.presentation.signin.SignInScreen

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
    val currentState = backstackState?.destination?.route
    var selectedItem by rememberSaveable { mutableIntStateOf(1) }

    Log.d("BottomNAv: ", "$selectedItem")
    selectedItem = when (currentState) {
        Route.ScheduleScreen.route -> 0
        Route.HomeScreen.route -> 1
        Route.MyPageScreen.route -> 2
        else -> selectedItem
    }

    val isMainTab = currentState in listOf(
        Route.ScheduleScreen.route,
        Route.HomeScreen.route,
        Route.MyPageScreen.route
    )


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isMainTab) {
                BottomNavigationBar(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index -> navigateTab(navController, index) }
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = it.calculateBottomPadding())
        ) {
            composable(route = Route.HomeScreen.route) {
                HomeScreen(navController = navController)
            }
            composable(route = Route.ScheduleScreen.route) {
                ScheduleScreen(hiltViewModel(), navController = navController)
            }
            navigation(
                route = Route.ScheduleScreenNavigation.route,
                startDestination = Route.ScheduleScreen.route
            ) {
                composable(route = Route.ScheduleAddScreen.route) {
                    ScheduleAddScreen(hiltViewModel(), navController = navController)
                }
            }
            composable(route = Route.MyPageScreen.route) {
                MyPageScreen(hiltViewModel(), navController)
            }
            navigation(
                route = Route.MyPageScreenNavigation.route,
                startDestination = Route.MyPageScreen.route
            ) {
                composable(route = Route.EditInfoScreen.route) {
                    EditInfoScreen(navController = navController)
                }
                composable(route = Route.ServiceScreen.route) {
//                    ServiceScreen()
                }
                composable(route = Route.WithdrawalScreen.route) {
                    WithdrawalScreen(navController = navController)
                }
                composable(route = Route.CabinetManageScreen.route) {
                    CabinetManageScreen(navController = navController) {
                        navController.navigate("cabinetRegisterScreen")
                    }
                }
                composable(route = Route.CabinetRegisterScreen.route) {
                    CabinetRegisterScreen(navController = navController)
                }
            }
            composable(route = Route.SignInScreen.route) {
                SignInScreen(navController = navController)
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
            launchSingleTop = true
            restoreState = true
        }
    }
}
