package com.example.pillinTimeAndroid.presentation.nvgraph

sealed class Route(
    val route: String
) {
    // SignIn & SignUp
    object SignInScreen : Route(route = "signinScreen")
    object RoleSelectScreen : Route(route = "roleSelectScreen")
    object SignUpManagerScreen : Route(route = "signupManagerScreen")
    object SignUpClientScreen : Route(route = "signupClientScreen")
    object SignUpLoadingScreen : Route(route = "signupLoadingScreen")

    object AppStartNavigation : Route(route = "appStartNavigation")
    object BottomNavigation : Route(route = "bottomNavigation")
    object BottomNavigatorScreen : Route(route = "bottomNavigator")
    object HomeScreen : Route(route = "homeScreen")
    object ScheduleScreen : Route(route = "scheduleScreen")
    object MyPageScreen : Route(route = "mypageScreen")

    object MyPageScreenNavigation : Route(route = "mypageScreenNavigation")
    object EditInfoScreen : Route(route = "editInfoScreen")
    object WithdrawalScreen : Route(route = "withdrawalScreen")
    object ServiceScreen : Route(route = "serviceScreen")
    object SubscribeScreen : Route(route = "subscribeScreen")
}