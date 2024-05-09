package com.example.pillinTimeAndroid.presentation.mypage.relation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme

@Composable
fun ClientManageScreen(

) {
    Column () {
        CustomTopBar(
            title = "피보호자 관리",
            showBackButton = true,
            onBackClicked = {},
        )
        Spacer(modifier = Modifier.height(37.dp))
        Text(
            text = "총 명",
            color = Gray90,
            style = PillinTimeTheme.typography.headline5Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {

        }
    }
}
