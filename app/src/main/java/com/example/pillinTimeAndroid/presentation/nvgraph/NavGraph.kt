package com.example.pillinTimeAndroid.presentation.nvgraph

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.pillinTimeAndroid.presentation.btmnavigator.BottomNavigator
import com.example.pillinTimeAndroid.presentation.signin.SignInScreen
import com.example.pillinTimeAndroid.presentation.signup.RoleSelectScreen
import com.example.pillinTimeAndroid.presentation.signup.SignUpClientScreen

@Composable
fun NavGraph(
    startDestination: String,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {

        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.SignInScreen.route
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
        }

        navigation(
            route = Route.BottomNavigation.route,
            startDestination = Route.BottomNavigatorScreen.route
        ) {
            composable(route = Route.BottomNavigatorScreen.route) {
                BottomNavigator()
            }
        }

        navigation(
            route = Route.AppStartNavigationV2.route,
            startDestination = Route.SignUpClientScreen.route
        ) {
            composable(route = Route.SignUpClientScreen.route) {
                BackHandler(true) {}
                SignUpClientScreen(hiltViewModel(), navController)
            }
            composable(route = Route.BottomNavigatorScreen.route) {
                BottomNavigator()
            }
        }
    }
}