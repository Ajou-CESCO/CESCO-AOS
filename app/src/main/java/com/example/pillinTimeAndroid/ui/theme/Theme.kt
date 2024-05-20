package com.example.pillinTimeAndroid.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

private val DefaultColorScheme = darkColorScheme(
    primary = Purple80, secondary = PurpleGrey80, tertiary = Pink80,
    background = White,
)

@Composable
fun PillinTimeAndroidTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DefaultColorScheme.primary.toArgb()
            window.navigationBarColor = DefaultColorScheme.background.toArgb()
        }
    }
    CompositionLocalProvider(
        LocalPillinTimeTypography provides Typography
    ) {
        MaterialTheme(
            colorScheme = DefaultColorScheme,
//            typography = Typography,
            content = content
        )
    }
}

object PillinTimeTheme {
    val typography: PillinTimeTypography
        @Composable
        get() = LocalPillinTimeTypography.current
}