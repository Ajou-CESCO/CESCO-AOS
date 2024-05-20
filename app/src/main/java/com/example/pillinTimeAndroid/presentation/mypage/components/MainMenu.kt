package com.example.pillinTimeAndroid.presentation.mypage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.presentation.Dimens
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun MainMenu(
    isManager: Boolean,
    onItemClick: (destination: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.BasicPadding)
    ) {
        Column(
            modifier = Modifier
                .clip(shapes.small)
                .background(White)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            MainMenuItem("내 정보 관리") {
                onItemClick("editInfoScreen")
            }
            if(isManager) MainMenuItem("구독 결제 내역") {
                onItemClick("subscribeScreen")
            }
            MainMenuItem("고객 센터") {
                onItemClick("serviceScreen")
            }
            MainMenuItem("로그아웃") {

            }
            MainMenuItem("회원 탈퇴") {
                onItemClick("withdrawalScreen")
            }
        }
    }
}

@Composable
fun MainMenuItem(
    title: String,
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val y = size.height
                drawLine(
                    color = Gray5,
                    start = Offset(x = 0f, y = y),
                    end = Offset(x = size.width, y = y),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .padding(horizontal = 22.dp, vertical = 17.dp)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        text = title,
        color = Gray90,
        style = PillinTimeTheme.typography.headline5Medium
    )
}