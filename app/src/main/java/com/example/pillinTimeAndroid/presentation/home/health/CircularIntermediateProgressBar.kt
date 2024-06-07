package com.example.pillinTimeAndroid.presentation.home.health

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.ui.theme.Gray10
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60

@Composable
fun CircularIntermediateProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 36f,
    color: Color = Primary60,
    text: String = "0"
) {
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1500, easing = LinearEasing),
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(modifier = modifier) {
            val diameter = size.minDimension
            val rect = Rect(
                Offset(strokeWidth / 2, strokeWidth / 2),
                Size(diameter - strokeWidth, diameter - strokeWidth)
            )

            drawArc(
                color = Gray10,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = rect.topLeft,
                size = rect.size,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )

            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = -360 * progressAnimation,
                useCenter = false,
                topLeft = rect.topLeft,
                size = rect.size,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )
        }
        Column(
            modifier = Modifier.padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                color = Gray90,
                style = PillinTimeTheme.typography.logo4Extra
            )
            Text(
                text = "kcal",
                color = Gray90,
                style = PillinTimeTheme.typography.logo5Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CircularIntermediateProgressBarPreview() {
    PillinTimeAndroidTheme {
//        CircularIntermediateProgressBar(
//            progress = .4f,
//            modifier = Modifier.size(130.dp),
//            strokeWidth = 36f,
//            color = Primary60
//        )
        CircularProgressbar(
            size = 110.dp,
            calorie = 130f
        )
    }

}