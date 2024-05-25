package com.example.pillinTimeAndroid.presentation.nvgraph

sealed class Route(
    val route: String
) {
    object SignInScreen : Route(route = "signinScreen")
    object RoleSelectScreen : Route(route = "roleSelectScreen")
    object SignUpClientScreen : Route(route = "signupClientScreen")

    object BottomNavigatorScreen : Route(route = "bottomNavigatorScreen")
    object HomeScreen : Route(route = "homeScreen")
    object HealthScreen : Route(route = "healthScreen")

    object ScheduleScreen : Route(route = "scheduleScreen")
    object ScheduleScreenNavigation : Route(route = "scheduleScreenNavigation")
    object ScheduleAddScreen : Route(route = "scheduleAddScreen")

    object MyPageScreen : Route(route = "mypageScreen")

    object MyPageScreenNavigation : Route(route = "mypageScreenNavigation")
    object EditInfoScreen : Route(route = "editInfoScreen")
    object WithdrawalScreen : Route(route = "withdrawalScreen")
    object ServiceScreen : Route(route = "serviceScreen")
    object SubscribeScreen : Route(route = "subscribeScreen")
    object CabinetManageScreen : Route(route = "cabinetManageScreen")
    object CabinetRegisterScreen : Route(route = "cabinetRegisterScreen")
    object RelationManageScreen : Route(route = "relationManageScreen")
}