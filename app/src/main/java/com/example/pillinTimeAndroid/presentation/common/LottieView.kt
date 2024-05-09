package com.example.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme

@Composable
fun LottieView(modifier: Modifier) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.signup)
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

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun LottieViewPreview() {
    PillinTimeAndroidTheme {
        LottieView(modifier = Modifier.size(200.dp))
    }
}