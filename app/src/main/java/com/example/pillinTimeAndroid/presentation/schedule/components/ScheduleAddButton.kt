package com.example.pillinTimeAndroid.presentation.schedule.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.ui.theme.Gray40
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun ScheduleAddButton(
    modifier: Modifier,
    onClick: () -> Unit,
    buttonState: Boolean = true
) {
    Button(
        modifier = modifier
            .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
        onClick = onClick,
        enabled = buttonState,
        shape = shapes.large,
        contentPadding = PaddingValues(16.dp, 8.dp, 20.dp, 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary60,
            disabledContainerColor = Gray40
        ),
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = White
        )
        Text(
            text = "약추가",
            color = White,
            style = PillinTimeTheme.typography.body1Bold
        )
    }
}