package com.whdaud.pillinTimeAndroid.presentation.home.health

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.whdaud.pillinTimeAndroid.ui.theme.Gray10
import com.whdaud.pillinTimeAndroid.ui.theme.Gray90
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.ui.theme.Primary60
import com.whdaud.pillinTimeAndroid.ui.theme.White

@Composable
fun CircularProgressbar(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    foregroundIndicatorColor: Color = Primary60,
    shadowColor: Color = Gray10,
    indicatorThickness: Dp = 12.dp,
    calorie: Float = 100f,
    recommended: Float = 100f,
    animationDuration: Int = 3000,
) {
    var dataUsageRemember by remember {
        mutableFloatStateOf(0f)
    }

    val dataUsageAnimate = animateFloatAsState(
        targetValue = dataUsageRemember,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = 1000
        )
    )
    LaunchedEffect(Unit) {
        dataUsageRemember = calorie
    }

    Box(
        modifier = modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
            .size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(size)
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(shadowColor, Gray10),
                    center = Offset(x = this.size.width / 2, y = this.size.height / 2),
                    radius = (size / 2 - indicatorThickness).toPx()
                ),
                radius = this.size.height / 2,
                center = Offset(x = this.size.width / 2, y = this.size.height / 2)
            )
            drawCircle(
                color = White,
                radius = (size / 2 - indicatorThickness).toPx(),
                center = Offset(x = this.size.width / 2, y = this.size.height / 2)
            )
            val sweepAngle = (dataUsageAnimate.value) * -360 / recommended
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round),
                size = Size(
                    width = (size - indicatorThickness).toPx(),
                    height = (size - indicatorThickness).toPx()
                ),
                topLeft = Offset(
                    x = (indicatorThickness / 2).toPx(),
                    y = (indicatorThickness / 2).toPx()
                )
            )
        }
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = (dataUsageAnimate.value).toInt().toString(),
                color = Gray90,
                style = PillinTimeTheme.typography.logo4Extra
            )
            Text(
                text = "kcal",
                color = Gray90,
                style = PillinTimeTheme.typography.body1Medium
            )
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}