package com.example.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.ui.theme.Gray100
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.util.fadeInEffect

@Composable
fun LoadingScreen(
    name: String,
    status: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LottieView(
            modifier = Modifier
                .fillMaxSize(),
            block = {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 10.dp)
                        .fadeInEffect(1000),
                    text = "${status} 완료!",
                    textAlign = TextAlign.Center,
                    color = Gray70,
                    style = PillinTimeTheme.typography.headline5Bold
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fadeInEffect(1000),
                    text = "${name}님,\n만나서 반갑습니다!",
                    textAlign = TextAlign.Center,
                    color = Gray100,
                    style = PillinTimeTheme.typography.logo2Extra
                )
            }
        )
    }
}