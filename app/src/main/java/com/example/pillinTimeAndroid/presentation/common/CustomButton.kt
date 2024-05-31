package com.example.pillinTimeAndroid.presentation.common

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.Dimens.BasicHeight
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.Gray50
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary5
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.shapes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class ButtonColor {
    FILLED, BLANK
}

enum class ButtonSize {
    SMALL, MEDIUM
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    filled: ButtonColor,
    size: ButtonSize,
    text: String,
    onClick:  () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(BasicHeight),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (filled == ButtonColor.FILLED) Primary60 else Gray5,
            disabledContainerColor = Gray5,
        ),
        shape = shapes.small,
        enabled = enabled
    ) {
        Text(
            text = text,
            style = if (size == ButtonSize.MEDIUM) PillinTimeTheme.typography.headline5Medium else PillinTimeTheme.typography.body1Medium,
            color = if (filled == ButtonColor.FILLED && enabled) Primary5 else Gray50
        )
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun BackButton(
    onClick: () -> Unit
) {
    var isClickable by remember { mutableStateOf(true) }
    Icon(
        modifier = Modifier
            .padding(top = 14.dp, start = 16.dp)
            .clickable(
                enabled = isClickable,
                onClick = {
                    if (isClickable) {
                        isClickable = false
                        onClick()
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(500)
                            isClickable = true
                        }
                    }
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        painter = painterResource(id = R.drawable.ic_back),
        tint = Gray90,
        contentDescription = "Back"
    )
}