package com.example.pillinTimeAndroid.presentation.common


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.ui.theme.Error90
import com.example.pillinTimeAndroid.ui.theme.Gray10
import com.example.pillinTimeAndroid.ui.theme.Gray30
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    state: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    trailIcon: Int? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    inputType: InputType = InputType.PLAIN
) {
    val (maxLength, keyboardType) = when (inputType) {
        InputType.NAME -> Pair(8, KeyboardType.Text)
        InputType.PHONE -> Pair(11, KeyboardType.Number)
        InputType.SSN -> Pair(7, KeyboardType.Number)
        InputType.OTP -> Pair(6, KeyboardType.Number)
        InputType.PLAIN -> Pair(20, KeyboardType.Text)
        InputType.SERIAL -> Pair(16, KeyboardType.Text)
    }
    val (backgroundColor, borderColor) = when {
        value.isNotEmpty() && state -> Pair(White, Primary60)
        value.isNotEmpty() && !state -> Pair(White, Error90)
        else -> Pair(Gray5, Gray10)
    }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    BasicTextField(
        modifier = Modifier
            .clip(shapes.small)
            .border(1.dp, borderColor, shapes.small)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = value,
        onValueChange = { newValue ->
            if (newValue.length <= maxLength) {
                onValueChange(newValue)
                if (newValue.length == maxLength) {
                    focusManager.clearFocus()
                }
            }
        },
        textStyle = PillinTimeTheme.typography.headline5Medium,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .background(backgroundColor)
                    .border(1.dp, borderColor, shapes.small)
                    .padding(horizontal = 22.dp, vertical = 17.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isNotEmpty() && trailIcon != null) {
                    IconButton(
                        onClick = { onValueChange("") },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(14.dp),
                    ) {
                        Icon(
                            painter = painterResource(id = trailIcon),
                            contentDescription = "Clear text",
                            tint = Color.Unspecified
                        )
                    }
                }
                innerTextField()
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        color = Gray30,
                        style = PillinTimeTheme.typography.headline5Medium
                    )
                }
            }
        }
    )
}

enum class InputType(val type: String) {
    NAME("name"),
    PHONE("phone"),
    SSN("ssn"),
    OTP("otp"),
    PLAIN("plain"),
    SERIAL("serial")
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun CustomTextFieldPreview() {
    PillinTimeAndroidTheme {
        CustomTextField(
            state = true,
            value = "",
            onValueChange = {},
            trailIcon = R.drawable.ic_cancel,
            hint = "dddddd"
        )
    }
}