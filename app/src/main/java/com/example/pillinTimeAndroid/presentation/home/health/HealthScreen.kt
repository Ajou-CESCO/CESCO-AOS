package com.example.pillinTimeAndroid.presentation.home.health

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.presentation.home.HomeViewModel
import com.example.pillinTimeAndroid.ui.theme.Gray40
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.Gray50
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes
import com.example.pillinTimeAndroid.util.fadeInEffect
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HealthScreen(
    navController: NavController,
    memberName: String?,
    memberId: Int?,
    isManager: Boolean?,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val remoteHealthData by viewModel.remoteHealthData.collectAsState()
    Log.e("remoteHealthDataremoteHealthDataremoteHealthData", "$remoteHealthData")
    val coloredText = buildAnnotatedString {
        append("오늘 ")
        withStyle(
            style = PillinTimeTheme.typography.logo3Medium.toSpanStyle().copy(color = Primary60)
        ) {
            append("$memberName")
        }
        append("님의\n건강상태를 분석했어요.")
    }
    LaunchedEffect(memberId, isManager) {
        if (memberId != null) {
            viewModel.getRemoteHealthData(memberId, true)
        }
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
            modifier = Modifier.padding(horizontal = 33.dp)
        ) {
            Text(
                text = coloredText,
                color = Gray90,
                style = PillinTimeTheme.typography.logo3Medium
            )
            val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = "${today} 일자",
                color = Gray70,
                style = PillinTimeTheme.typography.body1Regular
            )
            Column(
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    // first box
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(shape = shapes.small)
                            .background(White)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column {
                                Text(
                                    modifier = Modifier.padding(top = 12.dp, start = 16.dp),
                                    text = "걸음수",
                                    color = Gray50,
                                    style = PillinTimeTheme.typography.body1Medium
                                )
                                Text(
                                    modifier = Modifier.padding(top = 10.dp, start = 16.dp, end = 32.dp),
                                    text = remoteHealthData?.stepsMessage.toString(),
                                    color = Gray90,
                                    style = PillinTimeTheme.typography.body1Bold
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    modifier = Modifier.padding(top = 10.dp, start = 16.dp, end = 32.dp),
                                    text = "오늘 걸음수 ${remoteHealthData?.steps}보",
                                    color = Primary60,
                                    style = PillinTimeTheme.typography.body1Bold
                                )
                                Text(
                                    modifier = Modifier.padding(top = 2.dp, start = 16.dp, bottom = 16.dp),
                                    text = "${remoteHealthData?.ageGroup}대 평균 ${remoteHealthData?.averStep}보",
                                    color = Gray40,
                                    style = PillinTimeTheme.typography.body1Bold
                                )
                            }
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fadeInEffect(),
                                painter = painterResource(id = R.drawable.img_health_stats_outer),
                                contentDescription = null
                            )
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fadeInEffect(),
                                painter = painterResource(id = R.drawable.img_health_stats_inner),
                                contentDescription = null
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    // second box
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(shape = shapes.small)
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
                                modifier = Modifier.padding(top = 12.dp, start = 16.dp),
                                text = "${remoteHealthData?.sleepTimeMessage}",
                                color = Gray90,
                                style = PillinTimeTheme.typography.body1Bold
                            )
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 36.dp),
                            ){
                                Image(
                                    modifier = Modifier
                                        .padding(end = 12.dp)
                                        .fadeInEffect(),
                                    painter = painterResource(id = R.drawable.img_health_arrow),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit
                                )
                                Image(
                                    modifier = Modifier
                                        .fadeInEffect(),
                                    painter = painterResource(id = R.drawable.img_health_moon),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                modifier = Modifier.padding(top = 12.dp, start = 16.dp),
                                text = "오늘 수면 시간 ${remoteHealthData?.sleepTime}시간",
                                color = Primary60,
                                style = PillinTimeTheme.typography.body1Bold
                            )
                            Text(
                                modifier = Modifier.padding(top = 2.dp, start = 16.dp, bottom = 16.dp),
                                text = "${remoteHealthData?.ageGroup}대 평균 ${remoteHealthData?.recommendSleepTime}시간",
                                color = Gray40,
                                style = PillinTimeTheme.typography.body1Bold
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
                            .background(White)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ){
                            Text(
                                modifier = Modifier.padding(top = 12.dp, start = 16.dp),
                                text = "심장박동 수",
                                color = Gray50,
                                style = PillinTimeTheme.typography.body1Medium
                            )
                            Row(
                                modifier = Modifier.padding(top = 14.dp ,start = 16.dp),
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
                                    text = "${remoteHealthData?.heartRate}",
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
                                    .fillMaxWidth()
                                    .fadeInEffect(),
                                painter = painterResource(id = R.drawable.img_health_heartrate),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                                text = "${remoteHealthData?.ageGroup}대 평균 ${remoteHealthData?.heartRateMessage}",
                                color = Gray40,
                                style = PillinTimeTheme.typography.body2Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    //fourth box
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(shape = shapes.small)
                            .background(White)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 12.dp, start = 16.dp),
                                text = "활동량",
                                color = Gray50,
                                style = PillinTimeTheme.typography.body1Medium
                            )
                            CircularIntermediateProgressBar(
                                modifier = Modifier
                                    .padding(top = 24.dp, start = 12.dp)
                                    .size(130.dp),
                                progress = .4f,
                                text = "${remoteHealthData?.calorie}"
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                modifier = Modifier.padding(top = 12.dp, start = 16.dp, bottom = 16.dp),
                                text = "권장량 ${remoteHealthData?.calorieMessage}",
                                color = Gray50,
                                style = PillinTimeTheme.typography.body1Bold
                            )
                        }
                    }
                }
            }
        }
    }
}