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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInPage(
    state: Boolean,
    pageList: SignInPageList,
    input: String,
    onInputChanged: (String) -> Unit,
    visualTransformation: VisualTransformation,
    inputType: InputType
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (pageList == signInPages[0] || pageList == signInPages[2]) Spacer(modifier = Modifier.height(14.dp))
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
            if (!state) {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = pageList.error,
                    style = PillinTimeTheme.typography.body1Regular,
                    color = Error90,
                    lineHeight = 26.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (pageList == signInPages[1]) {
                CompositionLocalProvider(
                    LocalMinimumInteractiveComponentEnforcement provides false,
                ) {
                    TextButton(
                        modifier = Modifier.padding(top = 3.dp),
                        onClick = { /*TODO*/ }
                        // otp 재인증 API 호출 필요
                    ) {
                        Text(
                            text = "재전송",
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