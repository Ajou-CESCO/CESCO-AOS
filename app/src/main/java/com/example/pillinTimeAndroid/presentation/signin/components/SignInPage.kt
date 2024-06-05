package com.example.pillinTimeAndroid.presentation.signin.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.common.CustomTextField
import com.example.pillinTimeAndroid.presentation.common.InputType
import com.example.pillinTimeAndroid.ui.theme.Error90
import com.example.pillinTimeAndroid.ui.theme.Gray40
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInPage(
    modifier: Modifier,
    state: Boolean,
    pageList: SignInPageList,
    input: String,
    onInputChanged: (String) -> Unit,
    onSmsAuthClick: () -> Unit,
    visualTransformation: VisualTransformation,
    inputType: InputType,
    smsState: Boolean
) {
    var timer by remember { mutableStateOf(180) }
    var timerActive by remember { mutableStateOf(false) }

    LaunchedEffect(smsState) {
        if (smsState) {
            timerActive = true
        }
        while (timer > 0 && timerActive) {
            delay(1000L)
            timer--
        }
        timerActive = false
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (pageList == signInPages[0] || pageList == signInPages[2]) Spacer(
            modifier = Modifier.height(
                14.dp
            )
        )
        CustomTextField(
            state = state,
            hint = pageList.hint,
            value = input,
            onValueChange = onInputChanged,
            trailIcon = R.drawable.ic_cancel,
            visualTransformation = visualTransformation,
            inputType = inputType
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                if (!state) {
                    Text(
                        modifier = Modifier.padding(top = 12.dp, start = 8.dp),
                        text = pageList.error,
                        style = PillinTimeTheme.typography.body1Regular,
                        color = Error90,
                        lineHeight = 26.sp
                    )
                }
                if (pageList == signInPages[1]) {
                    CompositionLocalProvider(
                        LocalMinimumInteractiveComponentEnforcement provides false,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 14.dp, start = 8.dp),
                                text = "인증번호 유효 시간:\n${
                                    (timer / 60).toString().padStart(2, '0')
                                }분 ${(timer % 60).toString().padStart(2, '0')}초",
                                style = PillinTimeTheme.typography.body1Bold.copy(
                                    color = Primary60,
                                ),
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            TextButton(
                                modifier = Modifier.padding(top = 3.dp),
                                onClick = {
                                    onSmsAuthClick()
                                    timer = 180
                                    timerActive = true
                                }
                            ) {
                                Text(
                                    text = "인증번호 재전송",
                                    style = PillinTimeTheme.typography.body1Regular.copy(
                                        color = Gray40,
                                        textDecoration = TextDecoration.Underline
                                    ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}