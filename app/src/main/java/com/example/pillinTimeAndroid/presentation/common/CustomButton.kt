package com.example.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.Dimens.BasicHeight
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.Gray50
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary5
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.shapes

enum class ButtonColor {
    FILLED, BLANK
}

enum class ButtonSize {
    SMALL, MEDIUM
}

@Composable
fun CustomButton(
    modifier: Modifier,
    enabled: Boolean,
    filled: ButtonColor,
    size: ButtonSize,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(BasicHeight),
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

@Composable
fun BackButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back), contentDescription = "Back"
        )
    }
}