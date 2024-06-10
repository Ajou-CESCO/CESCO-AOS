package com.whdaud.pillinTimeAndroid.presentation.signin

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.whdaud.pillinTimeAndroid.presentation.common.ButtonColor
import com.whdaud.pillinTimeAndroid.presentation.common.ButtonSize
import com.whdaud.pillinTimeAndroid.presentation.common.CustomButton
import com.whdaud.pillinTimeAndroid.presentation.common.CustomTopBar
import com.whdaud.pillinTimeAndroid.presentation.common.GeneralScreen
import com.whdaud.pillinTimeAndroid.presentation.common.LoadingScreen
import com.whdaud.pillinTimeAndroid.presentation.signin.components.SignInPage
import com.whdaud.pillinTimeAndroid.presentation.signin.components.signInPages
import com.whdaud.pillinTimeAndroid.util.fadeInSlideEffect
import kotlinx.coroutines.delay

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navController: NavController,
) {
    val currentPage = viewModel.getCurrentPage()
    val currentPageTitle = viewModel.getCurrentPageTitle()
    val inputType = viewModel.getInputType()
    val isLoading by viewModel.isLoading.collectAsState()
    val userName = viewModel.name.value
    val smsState = remember { mutableStateOf(false) }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        RequestNotificationPermissionDialog()
    }

    if (isLoading) {
        LoadingScreen(userName, "로그인")
    } else {
        GeneralScreen(
            topBar = {
                CustomTopBar(
                    showBackButton = currentPage != signInPages[0],
                    onBackClicked = { viewModel.previousPage() }
                )
            },
            title = currentPageTitle,
            subtitle = currentPage.subtitle,
            content = {
                SignInPage(
                    modifier = Modifier.fadeInSlideEffect(delayMillis = 1000),
                    state = viewModel.isValidateInput(),
                    pageList = currentPage,
                    input = viewModel.getCurrentInput(),
                    onInputChanged = viewModel::updateInput,
                    visualTransformation = viewModel.getVisualTransformations(),
                    inputType = inputType,
                    onSmsAuthClick = { viewModel.postSmsAuth() },
                    smsState = smsState.value
                )
            }
        ) {
            val buttonText = if (currentPage == signInPages[3]) "확인" else "다음"
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.isValidateInput() && viewModel.getCurrentInput().isNotEmpty(),
                filled = ButtonColor.FILLED,
                size = ButtonSize.MEDIUM,
                text = buttonText,
                onClick = {
                    when (currentPage) {
                        signInPages[0] -> {
                            smsState.value = true
                            viewModel.nextPage()
                            viewModel.postSmsAuth()
                        }
                        signInPages[3] -> {
                            viewModel.signIn(navController)
                        }
                        else -> viewModel.nextPage()
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    LaunchedEffect(permissionState) {
        delay(1500)
        permissionState.launchPermissionRequest()
    }
}

