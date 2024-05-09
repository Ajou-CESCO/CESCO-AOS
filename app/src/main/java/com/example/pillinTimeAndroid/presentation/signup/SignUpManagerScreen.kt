package com.example.pillinTimeAndroid.presentation.signup

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pillinTimeAndroid.presentation.common.ButtonColor
import com.example.pillinTimeAndroid.presentation.common.ButtonSize
import com.example.pillinTimeAndroid.presentation.common.CustomButton
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.presentation.common.GeneralScreen
import com.example.pillinTimeAndroid.presentation.common.InputType
import com.example.pillinTimeAndroid.presentation.signup.components.SignUpPage
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.util.PhoneVisualTransformation

@Composable
fun SignUpManagerScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val currentPage = viewModel.getCurrentPage()

    GeneralScreen(
        topBar = {
            CustomTopBar()
        },
        title = currentPage.title,
        subtitle = currentPage.subtitle.toString(),
        content = {
            SignUpPage(
                state = viewModel.isValidateInput(),
                input = viewModel.getCurrentInput(),
                pageList = currentPage,
                onInputChanged = viewModel::updateInput,
                inputType = InputType.PHONE,
                visualTransformation = PhoneVisualTransformation()
            )
        },
        button = {
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.isValidateInput() && viewModel.getCurrentInput().isNotEmpty(),
                filled = ButtonColor.FILLED,
                size = ButtonSize.MEDIUM,
                text = "다음",
                onClick = {
                    // TODO: managerRequestAPI
                }
            )
        }
    )
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun SignUpManagerScreenPreview() {
    PillinTimeAndroidTheme {
        SignUpManagerScreen(hiltViewModel(), rememberNavController())
    }
}