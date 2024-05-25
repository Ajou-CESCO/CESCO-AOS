package com.example.pillinTimeAndroid.presentation.signup

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.presentation.common.ButtonColor
import com.example.pillinTimeAndroid.presentation.common.ButtonSize
import com.example.pillinTimeAndroid.presentation.common.CustomButton
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.presentation.common.GeneralScreen
import com.example.pillinTimeAndroid.presentation.common.LoadingScreen
import com.example.pillinTimeAndroid.presentation.signup.components.RoleSelector

@Composable
fun RoleSelectScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController,
) {
    var selectedRole by remember { mutableStateOf<Int?>(null) }
    val isLoading by viewModel.isLoading.collectAsState()
    val userName by viewModel.userName.collectAsState()

    if (isLoading) {
        LoadingScreen(userName, "회원가입")
    } else {
        GeneralScreen(
            topBar = {
                CustomTopBar(
                    showBackButton = true,
                    onBackClicked = {
                        navController.popBackStack()
                    },
                )
            },
            title = "회원님은 어떤 사람인가요?",
            subtitle = if (selectedRole == null) "본인 유형을 알려주세요" else "본인에 대한 간단한 정보를 알려주세요.",
            content = {
                RoleSelector(
                    selectedRole = selectedRole,
                    onRoleSelect = { role -> selectedRole = role })
            }
        ) {
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedRole != null,
                filled = ButtonColor.FILLED,
                size = ButtonSize.MEDIUM,
                text = "다음",
                onClick = {
                    val isManager = selectedRole == 1
                    viewModel.signUp(navController, isManager)
                }
            )
        }
    }
}
