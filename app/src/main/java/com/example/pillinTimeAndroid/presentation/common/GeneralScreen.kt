package com.example.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.ui.theme.Gray100
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme

@Composable
fun GeneralScreen(
    topBar: @Composable () -> Unit,
    title: String,
    subtitle: String,
    content: @Composable () -> Unit,
    button: @Composable () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .clickable(
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        topBar()
        Spacer(modifier = Modifier.height(26.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = BasicPadding)
        ) {
            Text(
                text = title,
                color = Gray100,
                style = PillinTimeTheme.typography.logo2Extra
            )
            Spacer(modifier = Modifier.height(8.dp))
            if(subtitle.isNotEmpty()) {
                Text(
                    text = subtitle,
                    color = Gray70,
                    style = PillinTimeTheme.typography.body2Regular
                )
            }
            Spacer(modifier = Modifier.height(33.dp))
            content()
            Spacer(modifier = Modifier.weight(1f))
        }
        Column(
            modifier = Modifier.padding(horizontal = BasicPadding)
        ) {
            button()
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun GeneralScreenPreview() {
    PillinTimeAndroidTheme {
        GeneralScreen(
            topBar = {
                CustomTopBar(
                    showProgressBar = true,
                    progress = 1 / 3f,
                    onBackClicked = { },
                    title = "전체 기능 포함"
                )
            },
            title = "새 계정 만들기",
            subtitle = "아래 정보를 입력하세요",
            content = { }
        ) {
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                filled = ButtonColor.FILLED,
                size = ButtonSize.MEDIUM,
                text = "dddd",
                onClick = {}
            )
        }
    }
}