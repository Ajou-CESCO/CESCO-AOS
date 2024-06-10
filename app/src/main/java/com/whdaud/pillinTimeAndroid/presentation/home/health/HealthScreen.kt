package com.whdaud.pillinTimeAndroid.presentation.home.health

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.whdaud.pillinTimeAndroid.R
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.HealthStatDTO
import com.whdaud.pillinTimeAndroid.presentation.common.CustomTopBar
import com.whdaud.pillinTimeAndroid.presentation.common.ext.buildStyledText
import com.whdaud.pillinTimeAndroid.ui.theme.Gray40
import com.whdaud.pillinTimeAndroid.ui.theme.Gray5
import com.whdaud.pillinTimeAndroid.ui.theme.Gray50
import com.whdaud.pillinTimeAndroid.ui.theme.Gray70
import com.whdaud.pillinTimeAndroid.ui.theme.Gray90
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.ui.theme.Primary60
import com.whdaud.pillinTimeAndroid.ui.theme.White
import com.whdaud.pillinTimeAndroid.ui.theme.shapes
import com.whdaud.pillinTimeAndroid.util.fadeInEffect
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun HealthScreen(
    navController: NavController,
    memberName: String?,
    healthData: HealthStatDTO
) {
    val coloredText = buildAnnotatedString {
        append("오늘 ")
        withStyle(
            style = PillinTimeTheme.typography.logo3Medium.toSpanStyle().copy(color = Primary60)
        ) {
            append(memberName)
        }
        append("님의\n건강상태를 분석했어요.")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray5)
    ) {
        CustomTopBar(
            onBackClicked = { navController.popBackStack() },
            showBackButton = true,
            title = "오늘의 건강상태"
        )
        Spacer(modifier = Modifier.height(29.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 33.dp)
                .padding(bottom = 100.dp)
        ) {
            Text(
                modifier = Modifier.fadeInEffect(400),
                text = coloredText,
                color = Gray90,
                style = PillinTimeTheme.typography.logo3Medium
            )
            val today = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            Text(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fadeInEffect(650),
                text = "$today 일자",
                color = Gray70,
                style = PillinTimeTheme.typography.body1Regular
            )
            Column(
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    // First box
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(shape = shapes.small)
                            .fadeInEffect(delayMillis = 900)
                            .background(White)
                    ) {
                        Column {
                            Text(
                                modifier = Modifier.padding(top = 12.dp, start = 16.dp),
                                text = "걸음수",
                                color = Gray50,
                                style = PillinTimeTheme.typography.body1Medium
                            )
                            Text(
                                modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 32.dp),
                                text = healthData.stepsMessage,
                                color = Gray90,
                                style = PillinTimeTheme.typography.body1Bold
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            HealthStyledText(
                                modifier = Modifier,
                                fullText = "오늘 걸음수 ${healthData.steps}보",
                                targetText = "${healthData.steps}보",
                                textColor = Primary60
                            )
                            HealthStyledText(
                                modifier = Modifier.padding(top = 2.dp, bottom = 16.dp),
                                fullText = "${healthData.ageGroup}대 평균 ${healthData.averStep}보",
                                targetText = "${healthData.averStep}보",
                                textColor = Gray40
                            )
                        }
                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            painter = painterResource(id = R.drawable.img_health_stats_outer),
                            contentDescription = null,
                            contentScale = ContentScale.Crop

                        )
                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            painter = painterResource(id = R.drawable.img_health_stats_inner),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    // Second box
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(shape = shapes.small)
                            .fadeInEffect(delayMillis = 900)
                            .background(White)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 12.dp, start = 16.dp),
                                text = "수면",
                                color = Gray50,
                                style = PillinTimeTheme.typography.body1Medium
                            )
                            Text(
                                modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp),
                                text = healthData.sleepTimeMessage,
                                color = Gray90,
                                style = PillinTimeTheme.typography.body1Bold
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 36.dp),
                            ) {
                                Image(
                                    modifier = Modifier.padding(end = 12.dp),
                                    painter = painterResource(id = R.drawable.img_health_arrow),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.img_health_moon),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            HealthStyledText(
                                modifier = Modifier.padding(top = 12.dp),
                                fullText = "오늘 수면 시간 ${healthData.sleepTime}시간",
                                targetText = "${healthData.sleepTime}시간",
                                textColor = Primary60
                            )
                            HealthStyledText(
                                modifier = Modifier.padding(top = 2.dp, bottom = 16.dp),
                                fullText = "${healthData.ageGroup}대 평균 ${healthData.recommendSleepTime}시간",
                                targetText = "${healthData.recommendSleepTime}시간",
                                textColor = Gray40
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    // third box
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(shape = shapes.small)
                            .fadeInEffect(delayMillis = 1200)
                            .background(White)
                        ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 12.dp, start = 16.dp),
                                text = "심장박동 수",
                                color = Gray50,
                                style = PillinTimeTheme.typography.body1Medium
                            )
                            Row(
                                modifier = Modifier.padding(top = 14.dp, start = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(end = 4.dp),
                                    painter = painterResource(id = R.drawable.ic_heart),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                                Text(
                                    text = "${healthData.heartRate}",
                                    color = Gray90,
                                    style = PillinTimeTheme.typography.logo3Extra
                                )
                                Text(
                                    modifier = Modifier.padding(start = 4.dp),
                                    text = "bpm",
                                    color = Gray90,
                                    style = PillinTimeTheme.typography.logo5Medium
                                )
                            }
                            Image(
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .fillMaxWidth(),
                                painter = painterResource(id = R.drawable.img_health_heartrate),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            HealthStyledText(
                                modifier = Modifier.padding(bottom = 16.dp),
                                fullText = "${healthData.ageGroup}대 평균\n${healthData.heartRateMessage}",
                                targetText = healthData.heartRateMessage,
                                textColor = Gray40
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    //fourth box
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(shape = shapes.small)
                            .fadeInEffect(delayMillis = 1200)
                            .background(White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 12.dp, start = 16.dp),
                                text = "활동량",
                                color = Gray50,
                                style = PillinTimeTheme.typography.body1Medium
                            )
                            val recommendedCal = healthData.calorieMessage.substring(0,4).toFloat()
                            val progress = (healthData.calorie.toFloat() / recommendedCal) * 100
                            Log.e("progress", "$recommendedCal, ${healthData.calorie} $progress")
                            CircularProgressbar(
                                modifier = Modifier.padding(top = 12.dp),
                                size = 120.dp,
                                calorie = healthData.calorie.toFloat(),
                                recommended = recommendedCal
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            HealthStyledText(
                                modifier = Modifier,
                                fullText = "활동량 ${healthData.calorie}kcal",
                                targetText = "${healthData.calorie}kcal",
                                textColor = Primary60
                            )
                            HealthStyledText(
                                modifier = Modifier.padding(top = 2.dp, bottom = 16.dp),
                                fullText = "권장량 ${healthData.calorieMessage}",
                                targetText = healthData.calorieMessage,
                                textColor = Gray40
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HealthStyledText(
    modifier: Modifier,
    fullText: String,
    targetText: String,
    textColor: Color
) {
    val targetStyle = PillinTimeTheme.typography.body1Bold.copy(color = textColor).toSpanStyle()
    val defaultStyle = PillinTimeTheme.typography.body1Regular
    Text(
        modifier = modifier.padding(start = 16.dp),
        text = buildStyledText(
            fullText = fullText,
            targetText = targetText,
            targetStyle = targetStyle,
            defaultStyle = defaultStyle
        ),
        style = defaultStyle,
        color = textColor
    )
}
