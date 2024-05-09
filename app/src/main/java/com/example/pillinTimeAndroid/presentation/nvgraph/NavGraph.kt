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
import com.example.pillinTimeAndroid.presentation.signup.SignUpManagerScreen
import com.example.pillinTimeAndroid.presentation.signup.SignUpViewModel

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    val signUpViewModel = hiltViewModel<SignUpViewModel>()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.SignInScreen.route
        ) {
            composable(route = Route.SignInScreen.route) {
                SignInScreen(hiltViewModel(), navController)
            }
            composable(route = Route.RoleSelectScreen.route)
            {
                BackHandler(true) {}
                RoleSelectScreen(signUpViewModel, navController)
            }
            composable(route = Route.SignUpManagerScreen.route) {
                BackHandler(true) {}
                SignUpManagerScreen(signUpViewModel, navController)
            }
            composable(route = Route.SignUpClientScreen.route) {
                BackHandler(true) {}
                SignUpClientScreen(signUpViewModel, navController)
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
    }
}