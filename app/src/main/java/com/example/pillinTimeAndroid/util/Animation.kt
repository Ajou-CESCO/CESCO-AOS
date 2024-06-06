package com.example.pillinTimeAndroid.util

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
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

@Composable
fun Modifier.fadeInSlideEffect(
    delayMillis: Int = 0,
    durationMillis: Int = 400
): Modifier = composed {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(delayMillis.toLong())
        isVisible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis)
    )
    val offset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 20f,
        animationSpec = tween(durationMillis)
    )

    this.alpha(alpha)
        .offset { IntOffset(0, offset.dp.roundToPx()) }
}

@Composable
fun Modifier.fadeInEffect(
    delayMillis: Int = 0,
    durationMillis: Int = 400
): Modifier = composed {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(delayMillis.toLong())
        isVisible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis)
    )

    this.alpha(alpha)
}