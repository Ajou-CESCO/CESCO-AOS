package com.example.pillinTimeAndroid.presentation.nvgraph

sealed class Route(
    val route: String
) {
    object SignInScreen : Route(route = "signinScreen")
    object SignupScreen : Route(route = "signupScreen")

    object HomeScreen : Route(route = "homeScreen")
    object ScheduleScreen : Route(route = "scheduleScreen")
    object MyPageScreen : Route(route = "mypageScreen")

    // sub navigation
    object AppStartNavigation : Route(route = "appStartNavigation")

    object BottomNavigation : Route(route = "bottomNavigation")
    object BottomNavigatorScreen : Route(route = "bottomNavigator")

}