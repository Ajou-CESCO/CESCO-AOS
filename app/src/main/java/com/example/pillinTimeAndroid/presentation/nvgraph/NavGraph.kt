package com.example.pillinTimeAndroid.presentation.nvgraph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.pillinTimeAndroid.presentation.btmnavigator.BottomNavigator


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,

    ) {
        navigation(
            route = Route.BottomNavigation.route,
            startDestination = Route.BottomNavigatorScreen.route
        ) {
            composable(route = Route.BottomNavigatorScreen.route){
                BottomNavigator()
            }
        }
    }
}