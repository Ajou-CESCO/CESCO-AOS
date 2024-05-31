package com.example.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.ui.theme.Gray100
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary5
import com.example.pillinTimeAndroid.ui.theme.shapes
import com.example.pillinTimeAndroid.util.fadeInEffect

@Composable
fun CustomToast(
    text: String,
    duration: Long = 2000L,
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(duration)
        onDismiss()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fadeInEffect()
            .padding(horizontal = BasicPadding)
            .clip(shapes.small)
            .background(Gray100)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 15.dp)
                .clip(shapes.small),
            text = text,
            color = Primary5,
            style = PillinTimeTheme.typography.body1Medium
        )
    }
}

@Composable
fun CustomToastHost(
    message: String,
    showToast: Boolean,
    onToastDismiss: () -> Unit
) {
    if (showToast) {
        CustomToast(
            text = message,
            onDismiss = onToastDismiss
        )
    }
}