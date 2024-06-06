package com.example.pillinTimeAndroid.presentation.nvgraph

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.data.remote.dto.response.HealthStatDTO
import com.example.pillinTimeAndroid.presentation.home.HomeScreen
import com.example.pillinTimeAndroid.presentation.home.health.HealthScreen
import com.example.pillinTimeAndroid.presentation.mypage.MyPageScreen
import com.example.pillinTimeAndroid.presentation.mypage.cabinet.CabinetRegisterScreen
import com.example.pillinTimeAndroid.presentation.mypage.editinfo.EditInfoScreen
import com.example.pillinTimeAndroid.presentation.mypage.editschedule.EditScheduleScreen
import com.example.pillinTimeAndroid.presentation.mypage.relation.RelationManageScreen
import com.example.pillinTimeAndroid.presentation.mypage.withdrawal.WithdrawalScreen
import com.example.pillinTimeAndroid.presentation.onboarding.OnBoardingScreen
import com.example.pillinTimeAndroid.presentation.schedule.ScheduleScreen
import com.example.pillinTimeAndroid.presentation.schedule.medicine.ScheduleAddScreen
import com.example.pillinTimeAndroid.presentation.signin.SignInScreen
import com.example.pillinTimeAndroid.presentation.signup.RoleSelectScreen
import com.example.pillinTimeAndroid.presentation.signup.SignUpClientScreen
import com.google.gson.Gson

@Composable
fun NavGraph(
    startDestination: String,
    appEntry: Boolean
) {
    val navController = rememberNavController()
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_tab_schedule),
            BottomNavigationItem(icon = R.drawable.ic_tab_home),
            BottomNavigationItem(icon = R.drawable.ic_tab_mypage),
        )
    }
    val backstackState = navController.currentBackStackEntryAsState().value
    val currentState = backstackState?.destination?.route
    var selectedItem by rememberSaveable { mutableIntStateOf(1) }
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
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            navController = navController,
            startDestination = startDestination,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            composable(route = Route.SignInScreen.route) {
                SignInScreen(navController = navController)
            }
            composable(route = Route.RoleSelectScreen.route)
            {
                BackHandler(true) {}
                RoleSelectScreen(navController = navController)
            }
            composable(route = Route.SignUpClientScreen.route) {
                BackHandler(true) {}
                SignUpClientScreen(navController = navController)
            }
            composable(route = Route.OnBoardingScreen.route) {
                BackHandler(true) {}
                OnBoardingScreen(navController = navController)
            }
            navigation(
                route = Route.BottomNavigatorScreen.route,
                startDestination = Route.HomeScreen.route
            ) {
                composable(route = Route.OnBoardingScreen.route) {
                    OnBoardingScreen(navController = navController)
                }
                composable(route = Route.HomeScreen.route) {
                    HomeScreen(navController = navController)
                }
                composable(
                    route = "${Route.HealthScreen.route}/{memberName}/{healthData}",
                    arguments = listOf(
                        navArgument("memberName") {
                            type = NavType.StringType
                        },
                        navArgument("healthData") {
                            type = NavType.StringType
                        }
                    )
                ) {entry ->
                    val memberName = entry.arguments?.getString("memberName")
                    val healthDataJson = entry.arguments?.getString("healthData")
                    val healthData = Gson().fromJson(healthDataJson, HealthStatDTO::class.java)
                    HealthScreen(
                        navController = navController,
                        memberName = memberName,
                        healthData = healthData
                    )
                }
                composable(
                    route = "${Route.CabinetRegisterScreen.route}/{memberId}",
                    arguments = listOf(
                        navArgument("memberId") {
                            type = NavType.IntType
                        }
                    )
                ) { entry ->
                    val memberId = entry.arguments?.getInt("memberId")
                    CabinetRegisterScreen(
                        navController = navController,
                        ownerId = memberId
                    )
                }
                composable(route = Route.ScheduleScreen.route) {
                    ScheduleScreen(navController = navController)
                }
                navigation(
                    route = Route.ScheduleScreenNavigation.route,
                    startDestination = Route.ScheduleScreen.route
                ) {
                    composable(
                        route = "${Route.ScheduleAddScreen.route}/{memberId}",
                        arguments = listOf(
                            navArgument("memberId") {
                                type = NavType.IntType
                            }
                        )
                    ) { entry ->
                        val memberId = entry.arguments?.getInt("memberId")
                        ScheduleAddScreen(navController = navController, memberId = memberId)
                    }
                }
                composable(route = Route.MyPageScreen.route) {
                    MyPageScreen(navController = navController)
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
                    composable(
                        route = "${Route.RelationManageScreen.route}/{title}",
                        arguments = listOf(
                            navArgument("title") {
                                type = NavType.StringType
                            }
                        )
                    ) { entry ->
                        val title = entry.arguments?.getString("title")
                        RelationManageScreen(navController = navController, title = title)
                    }
                    composable(route = Route.EditScheduleScreen.route) {
                        EditScheduleScreen(navController = navController)
                    }
                }
                composable(route = Route.SignInScreen.route) {
                    SignInScreen(navController = navController)
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
            launchSingleTop = true
            restoreState = true
        }
    }
}