package com.example.pillinTimeAndroid.presentation.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.presentation.common.BackButton
import com.example.pillinTimeAndroid.presentation.common.BasicButton
import com.example.pillinTimeAndroid.presentation.signin.components.SignInPage
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val currentPage = viewModel.getCurrentPage()
    val keyboardOptions = viewModel.getKeyboardOptions()
    Column(
        modifier = Modifier
            .padding(
                start = 7.dp, top = 5.dp, bottom = 16.dp
            )
            .imePadding()
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (viewModel.currentPageIndex.value != 0) {
            BackButton(
                onClick = {
                    viewModel.previousPage()
                })
        } else {
            Spacer(modifier = Modifier.padding(24.dp))
        }
        SignInPage(
            state = viewModel.validateInput(),
            page = currentPage,
            input = viewModel.getCurrentInput(),
            onInputChanged = viewModel::updateInput,
            keyboardOptions = keyboardOptions
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BasicPadding)
                .padding(bottom = 12.dp)
                .navigationBarsPadding(),
        ) {

            BasicButton(
                enabled = viewModel.validateInput() && viewModel.getCurrentInput().isNotEmpty(),
                text = "다음",
                onClick = {
                    if (currentPage == pages[2]) {
                        viewModel.nextPage()
                        // API 호출 (OPT 검증)
                    } else {
                        viewModel.nextPage()
                    }
                }
            )
        }
    }
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