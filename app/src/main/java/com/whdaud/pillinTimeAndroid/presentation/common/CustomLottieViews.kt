package com.whdaud.pillinTimeAndroid.presentation.common

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.whdaud.pillinTimeAndroid.R

@Composable
fun LoadingLottieView(
    modifier: Modifier,
    block: @Composable () -> Unit
) {
    val preloaderLottieCompositionV1 by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.signup)
    )
    val preloaderProgressV1 by animateLottieCompositionAsState(
        preloaderLottieCompositionV1,
        speed = .5f,
        iterations = 1,
        isPlaying = true
    )
    val preloaderLottieCompositionV2 by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.background)
    )
    val preloaderProgressV2 by animateLottieCompositionAsState(
        preloaderLottieCompositionV2,
        speed = 1f,
        iterations = 1,
        isPlaying = true
    )
    Box(modifier = modifier.fillMaxSize()) {
        LottieAnimation(
            composition = preloaderLottieCompositionV2,
            progress = { preloaderProgressV2 },
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier.size(250.dp),
                composition = preloaderLottieCompositionV1,
                progress = { preloaderProgressV1 },
            )
            block()
        }
    }
}

@Composable
fun CustomLottieView(
    modifier: Modifier = Modifier,
    @RawRes lottieAnim: Int
) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(lottieAnim)
    )
    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        speed = 1f,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        restartOnPlay = true
    )
    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = { preloaderProgress },
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}

@Composable
fun CustomLottieViewV2(
    modifier: Modifier = Modifier,
    @RawRes lottieAnim: Int
) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(lottieAnim)
    )
    var isPlaying by remember { mutableStateOf(true) }
    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        speed = .5f,
        iterations = 1,
        isPlaying = isPlaying,
    )
    LaunchedEffect(preloaderProgress) {
        if (preloaderProgress >= .6f) {
            isPlaying = false
        }
    }
    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = { preloaderProgress },
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}