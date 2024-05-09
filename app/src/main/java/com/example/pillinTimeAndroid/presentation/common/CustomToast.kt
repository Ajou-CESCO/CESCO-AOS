package com.example.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.ui.theme.Gray100
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary5
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun CustomToast(
    text: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapes.small)
            .background(Gray100)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 15.dp)
                .clip(shapes.small),
            text = text,
            color = Primary5,
            style = PillinTimeTheme.typography.body1Medium
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun CustomToastPreview() {
    PillinTimeAndroidTheme {
        CustomToast("정보 수정이 완료되었어요.")
    }
}