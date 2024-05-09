package com.example.pillinTimeAndroid.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.ui.theme.Error60
import com.example.pillinTimeAndroid.ui.theme.Gray30
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun DoseCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapes.small),
    ) {
        DoseItem()
        DoseItem(state = 1)
        DoseItem(state = 0)
        DoseItem(state = 2)
    }
}

@Composable
fun DoseItem(
    state: Int = 2
) {
    val doseColor = when (state) {
        1 -> Primary60
        2 -> Error60
        else -> Gray30
    }
    val doseStatus = when (state) {
        1 -> "완료"
        2 -> "미완료"
        else -> "예정"
    }
    Row(
        modifier = Modifier
            .padding(
                vertical = 10.dp,
                horizontal = 8.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.5.dp)
                .padding(start = 12.dp),
            text = "비타민 C정",
            style = PillinTimeTheme.typography.headline5Bold,
            color = Gray90
        )
        Text(
            modifier = Modifier
                .weight(.3f)
                .padding(end = 7.dp),
            textAlign = TextAlign.Center,
            text = "오후 4시",
            style = PillinTimeTheme.typography.body2Medium,
            color = Gray70
        )
        Text(
            modifier = Modifier
                .weight(.3f)
                .padding(end = 7.dp),
            textAlign = TextAlign.Center,
            text = doseStatus,
            style = PillinTimeTheme.typography.logo4Extra,
            color = doseColor
        )
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun DoseCardPreview() {
    PillinTimeAndroidTheme {
        DoseCard()
    }
}