package com.example.pillinTimeAndroid.presentation.signup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.data.remote.dto.response.RelationReqResponse
import com.example.pillinTimeAndroid.presentation.common.CustomAlertDialog
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary20
import com.example.pillinTimeAndroid.ui.theme.Primary5
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun ManagerRequestList(
    managers: List<RelationReqResponse>,
    onConfirm: (Int) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    var selectedManagerName by remember { mutableStateOf("") }
    var requestId by remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(managers) { manager ->
                Row(
                    modifier = Modifier
                        .border(1.dp, color = Primary20, shapes.small)
                        .clip(shapes.small)
                        .fillMaxWidth()
                        .background(Primary5)
                        .clickable(
                            onClick = {
                                selectedManagerName = manager.senderName
                                requestId = manager.id
                                showDialog.value = true
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(22.dp),
                        text = manager.senderName,
                        color = Gray90,
                        style = PillinTimeTheme.typography.headline4Bold
                    )
                    Text(
                        modifier = Modifier.padding(end = 22.dp),
                        text = manager.senderPhone.substring(9),
                        color = Gray70,
                        style = PillinTimeTheme.typography.headline5Regular
                    )
                }
            }
        }
        if (showDialog.value) {
            CustomAlertDialog(
                title = "${selectedManagerName}님을 보호자로\n수락하시겠어요?",
                description = "수락을 선택하면 ${selectedManagerName}님이 회원님의\n약 복용 현황과 건강 상태를 관리할 수 있어요.",
                confirmText = "수락할게요",
                dismissText = "거절할게요",
                onConfirm = { onConfirm(requestId) },
                onDismiss = { showDialog.value = false }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(100.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.White.copy(alpha = 1f)),
                        startY = 0f,
                        endY = 100f
                    )
                ),
        )
    }
}