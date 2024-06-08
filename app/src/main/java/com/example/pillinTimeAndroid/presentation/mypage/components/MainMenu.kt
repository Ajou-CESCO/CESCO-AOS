package com.example.pillinTimeAndroid.presentation.mypage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.presentation.Dimens
import com.example.pillinTimeAndroid.presentation.common.CustomAlertDialog
import com.example.pillinTimeAndroid.ui.theme.Gray10
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun MainMenu(
    isManager: Boolean,
    onItemClick: (destination: String) -> Unit,
    onLogoutClick: () -> Unit
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
            val showDialog = remember { mutableStateOf(false) }

            MainMenuItem(title = "내 정보 관리") {
                onItemClick("editInfoScreen")
            }
            if (isManager) MainMenuItem(title = "결제 내역") {
                onItemClick("subscribeScreen")
            }
            MainMenuItem(title = "로그아웃") {
                showDialog.value = true
            }
            MainMenuItem(title = "회원 탈퇴") {
                onItemClick("withdrawalScreen")
            }
            if (showDialog.value) {
                CustomAlertDialog(
                    title = "로그아웃",
                    description = "로그아웃하시겠습니까?",
                    confirmText = "로그아웃",
                    dismissText = "닫기",
                    onConfirm = onLogoutClick,
                    onDismiss = { showDialog.value = false }
                )
            }
        }
    }
}

@Composable
fun MainMenuItem(
    title: String,
    onClick: () -> Unit = {},
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
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
    HorizontalDivider(color = Gray10)
}