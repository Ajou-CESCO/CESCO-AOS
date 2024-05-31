package com.example.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun PermissionDialog(onRequestPermission: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            title = {
                Text(
                    text = "'약속시간'에서 알림을 보내고자 합니다",
                    color = Gray90,
                    style = PillinTimeTheme.typography.headline5Bold
                )
            },
            text = {
                Text(
                    text = "해당 기기로 서비스 이용에 필요한 안내 사항을 푸시 알림으로 보내드리겠습니다.\n\n앱 푸시에 수신 동의하시겠습니까?",
                    color = Gray70,
                    style = PillinTimeTheme.typography.body2Medium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onRequestPermission()
                        showWarningDialog = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp, 16.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Primary60,
                        contentColor = White
                    ),
                    shape = shapes.small
                ) {
                    Text(
                        text = "확인",
                        style = PillinTimeTheme.typography.headline5Medium
                    )
                }
            },
            onDismissRequest = { },
            shape = shapes.small,
            containerColor = White
        )
    }
}

@Composable
fun RationaleDialog() {
    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            title = {
                Text(
                    text = "'약속시간'에서 알림을 보내고자 합니다",
                    color = Gray90,
                    style = PillinTimeTheme.typography.headline5Bold
                )
            },
            text = {
                Text(
                    text = "해당 기기로 서비스 이용에 필요한 안내 사항을 푸시 알림으로 보내드리겠습니다.\n\n앱 푸시에 수신 동의하시겠습니까?",
                    color = Gray70,
                    style = PillinTimeTheme.typography.body2Medium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { showWarningDialog = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp, 16.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Primary60,
                        contentColor = White
                    ),
                    shape = shapes.small
                ) {
                    Text(
                        text = "확인",
                        style = PillinTimeTheme.typography.headline5Medium
                    )
                }
            },
            onDismissRequest = { showWarningDialog = false },
            shape = shapes.small,
            containerColor = White
        )
    }
}