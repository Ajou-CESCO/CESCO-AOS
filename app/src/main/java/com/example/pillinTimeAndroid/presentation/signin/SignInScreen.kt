package com.example.pillinTimeAndroid.presentation.signin

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pillinTimeAndroid.presentation.common.ButtonColor
import com.example.pillinTimeAndroid.presentation.common.ButtonSize
import com.example.pillinTimeAndroid.presentation.common.CustomButton
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.presentation.common.GeneralScreen
import com.example.pillinTimeAndroid.presentation.signin.components.SignInPage
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val currentPage = viewModel.getCurrentPage()
    val keyboardOptions = viewModel.getKeyboardOptions()

    GeneralScreen(
        topBar = {
            CustomTopBar(
                showBackButton = currentPage != inPages[0],
                onBackClicked = { viewModel.previousPage() }
            )
        },
        title = currentPage.title,
        subtitle = currentPage.subtitle,
        content = {
            SignInPage(
                state = viewModel.validateInput(),
                inPage = currentPage,
                input = viewModel.getCurrentInput(),
                onInputChanged = viewModel::updateInput,
                keyboardOptions = keyboardOptions
            )
        },
        button = {
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.validateInput() && viewModel.getCurrentInput().isNotEmpty(),
                filled = ButtonColor.FILLED,
                size = ButtonSize.MEDIUM,
                text = if (currentPage == inPages[2]) "확인" else "다음",
                onClick = {
                    if (currentPage == inPages[2]) {
                        viewModel.nextPage()
                        // API 호출 (OPT 검증)
                    } else {
                        viewModel.nextPage()
                    }
                }
            )
        }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SingInScreenPreview() {
    PillinTimeAndroidTheme {
        SignInScreen(SignInViewModel())
    }
}