package com.example.pillinTimeAndroid.presentation.signup

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.presentation.common.ButtonColor
import com.example.pillinTimeAndroid.presentation.common.ButtonSize
import com.example.pillinTimeAndroid.presentation.common.CustomAlertDialog
import com.example.pillinTimeAndroid.presentation.common.CustomButton
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.presentation.common.GeneralScreen
import com.example.pillinTimeAndroid.presentation.common.LoadingScreen
import com.example.pillinTimeAndroid.presentation.signup.components.RoleSelector
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60

@Composable
fun RoleSelectScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController,
) {
    var selectedRole by remember { mutableStateOf<Int?>(null) }
    val isLoading by viewModel.isLoading.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val showDialog = remember { mutableStateOf(false) }

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
                onClick = { showDialog.value = true }
            )
        }
        if(showDialog.value) {
            val textStyle = PillinTimeTheme.typography.headline4Bold.copy(color = Gray90)
            val (isManager, role) =
                if(selectedRole == 1) Pair(true, "보호자") else Pair(false, "피보호자")
            val roleSelectText = buildAnnotatedString {
                append("나의 역할을\n")
                withStyle(style = textStyle.toSpanStyle().copy(color = Primary60)) {
                    append("누군가의 보호를 받는 $role")
                }
                append("로\n결정하시겠어요?")
            }
            CustomAlertDialog(
                title = "",
                description = "나의 역할은 한 번 선택하면 변경하지 못해요.\n신중하게 선택해주세요.",
                confirmText = "확정하기",
                onConfirm = {  viewModel.signUp(navController, isManager) },
                dismissText = "다시 정하기",
                onDismiss = { showDialog.value = false },
                colorString = roleSelectText
            )
        }
    }
}
