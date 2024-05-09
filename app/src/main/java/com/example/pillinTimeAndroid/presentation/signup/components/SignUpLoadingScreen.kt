package com.example.pillinTimeAndroid.presentation.signup.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.presentation.common.LottieView
import com.example.pillinTimeAndroid.ui.theme.Gray100
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme

@Composable
fun SignUpLoadingScreen(
    name: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(BasicPadding),
        contentAlignment = Alignment.TopCenter
    ) {
        LottieView(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxHeight(0.45f),
            text = "${name}님,\n만나서 반갑습니다!",
            textAlign = TextAlign.Center,
            color = Gray100,
            style = PillinTimeTheme.typography.logo2Extra
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignUpLoadingPagePreview() {
    PillinTimeAndroidTheme {
        SignUpLoadingScreen("나지켜")
    }
}