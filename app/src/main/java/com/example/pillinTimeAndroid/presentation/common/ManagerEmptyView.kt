package com.example.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.Gray60
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary90
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun ManagerEmptyView(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray5),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = "등록된 피보호자가 없어요.",
            color = Gray90,
            style = PillinTimeTheme.typography.headline5Bold
        )
        Text(
            modifier = Modifier.padding(bottom = 35.dp),
            text = "피보호자를 등록하고\n피보호자의 복약일정을 케어해보세요",
            color = Gray60,
            style = PillinTimeTheme.typography.caption1Regular,
            textAlign = TextAlign.Center
        )
        Button(
            modifier = Modifier
                .padding(bottom = 9.dp)
                .size(238.dp, 48.dp),
            onClick = { navController.navigate("") },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = White,
                contentColor = Primary90
            ),
            shape = shapes.small
        ) {
            Text(
                text = "피보호자 등록하기",
                style = PillinTimeTheme.typography.body1Medium
            )
        }
        Button(
            modifier = Modifier
                .padding(bottom = 9.dp)
                .size(238.dp, 48.dp),
            onClick = { navController.navigate("") },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = White,
                contentColor = Primary90
            ),
            shape = shapes.small
        ) {
            Text(
                text = "피보호자 신청목록 보러 가기",
                style = PillinTimeTheme.typography.body1Medium
            )
        }
    }
}