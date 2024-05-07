package com.example.pillinTimeAndroid.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary40
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun HealthCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = shapes.small)
            .background(Primary60),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        HealthItem()
        HealthItem()
        HealthItem()
    }
}

@Composable
fun HealthItem() {
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        Text(
            text = "걸음 수",
            style = PillinTimeTheme.typography.caption1Medium,
            color = Primary40
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "99,110보",
            style = PillinTimeTheme.typography.body1Bold,
            color = White
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun HealthCardPreview() {
    PillinTimeAndroidTheme {
        HealthCard()
    }
}