package com.example.pillinTimeAndroid.util

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Modifier.fadeInSlideUpAnimation(
    animationDuration: Int = 300,
    offsetY: Float = 50f
): Modifier {
    val alpha = remember { Animatable(0f) }
    val translateY = remember { Animatable(offsetY) }

    CoroutineScope(rememberCoroutineScope().coroutineContext).launch {
        alpha.animateTo(1f, animationSpec = tween(durationMillis = animationDuration))
        translateY.animateTo(0f, animationSpec = tween(durationMillis = animationDuration))
    }

    return this
        .graphicsLayer {
            this.alpha = alpha.value
            this.translationY = translateY.value
        }
}