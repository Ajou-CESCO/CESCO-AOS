package com.example.pillinTimeAndroid.presentation.schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.home.components.DoseItem
import com.example.pillinTimeAndroid.ui.theme.Error40
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary40
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun ScheduleCard(
    state: Int
) {
    val scheduleList = listOf(
        "비타민"
    )
    val pillColor = when(state) {
        0 -> Color.Unspecified
        1 -> Primary40
        2 -> Error40
        else -> Color.Unspecified
    }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                painter = painterResource(id = R.drawable.ic_pill),
                contentDescription = "",
                tint = pillColor
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "예정된 약속시간",
                color = Gray70,
                style = PillinTimeTheme.typography.body2Medium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .clip(shapes.small)
                .background(White)
        ) {
            DoseItem()
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun ScheduleItemsPreview() {
    PillinTimeAndroidTheme {
        ScheduleCard(1)
    }
}