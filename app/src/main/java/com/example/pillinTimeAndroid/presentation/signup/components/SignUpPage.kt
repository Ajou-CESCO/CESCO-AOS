package com.example.pillinTimeAndroid.presentation.signup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.common.CustomTextField
import com.example.pillinTimeAndroid.presentation.common.InputType
import com.example.pillinTimeAndroid.ui.theme.Error90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme

@Composable
fun SignUpPage(
    state: Boolean,
    pageList: SignUpPageList,
    input: String,
    onInputChanged: (String) -> Unit,
    visualTransformation: VisualTransformation,
    inputType: InputType
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomTextField(
            state = state,
            hint = pageList.hint!!,
            value = input,
            onValueChange = onInputChanged,
            trailIcon = R.drawable.ic_cancel,
            visualTransformation = visualTransformation,
            inputType = inputType
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (!state) {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = pageList.error!!,
                    style = PillinTimeTheme.typography.body1Regular,
                    color = Error90,
                    lineHeight = 26.sp
                )
            }
        }
    }
}