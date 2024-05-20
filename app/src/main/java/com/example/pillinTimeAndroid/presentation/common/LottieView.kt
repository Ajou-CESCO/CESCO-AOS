package com.example.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pillinTimeAndroid.R

@Composable
fun LottieView(modifier: Modifier) {
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
        speed = .5f,
        iterations = 1,
        isPlaying = true
    )
    Box(modifier = modifier.fillMaxSize()) {
//        LottieAnimation(
//            composition = preloaderLottieCompositionV2,
//            progress = {preloaderProgressV2},
//            modifier = modifier.fillMaxSize()
//        )
        LottieAnimation(
            composition = preloaderLottieCompositionV1,
            progress = {preloaderProgressV1},
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun LottieViewV2(modifier: Modifier) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.background)
    )
    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        speed = .5f,
        iterations = 1,
        isPlaying = true
    )
    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = {preloaderProgress},
        modifier = modifier
    )
}