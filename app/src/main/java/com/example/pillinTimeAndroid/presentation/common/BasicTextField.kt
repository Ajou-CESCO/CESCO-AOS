package com.example.pillinTimeAndroid.presentation.common


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.Dimens.BasicSize
import com.example.pillinTimeAndroid.ui.theme.Error90
import com.example.pillinTimeAndroid.ui.theme.Gray10
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.Gray50
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    state: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    colors: TextFieldColors = colors(
        unfocusedContainerColor = Gray5,
        unfocusedLabelColor = Gray50,
        unfocusedIndicatorColor = Color.Transparent,
        focusedContainerColor = Gray5,
        focusedTextColor = Gray90,
        focusedIndicatorColor = Color.Transparent
    )
) {
    val borderColor = when {
        value.isNotEmpty() && state -> Primary60
        value.isNotEmpty() && !state -> Error90
        else -> Gray10
    }
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = PillinTimeTheme.typography.headline5Medium,
        keyboardOptions = keyboardOptions,
        shape = shapes.small,
        colors = colors,
        modifier = modifier
            .height(BasicSize)
            .fillMaxWidth()
            .border(1.dp, borderColor, shapes.small),
        placeholder = {
            Text(hint)
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        modifier = modifier.size(14.dp),
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = "Clear text",
                        tint = Color.Unspecified
                    )
                }
            }
        }
    )
}