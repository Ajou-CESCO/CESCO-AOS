package com.example.pillinTimeAndroid.presentation.mypage.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.data.remote.dto.UserDTO
import com.example.pillinTimeAndroid.presentation.common.CustomTextField
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme

@Composable
fun UserInfoDetailScreen(
    user: UserDTO<Any>,
    onValueChange: (String) -> Unit
) {
    Column {
        CustomTopBar(
            title = if (user.isManager) "피보호자 정보 수정" else "정보 수정",
            showBackButton = true,
            onBackClicked = {},
            showDeleteButton = user.isManager,
            onDeleteClicked = {}
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
        ) {
            EditInfoAfterItem("성명", user.name, onValueChange)
            EditInfoAfterItem("휴대폰 번호", user.phone, onValueChange)
            EditInfoAfterItem("주민등록번호", user.ssn, onValueChange)
        }
    }
    // TODO: 사용자 정보 띄워주고, onclick 받아서 patch 또는 삭제 구현
}

@Composable
fun EditInfoAfterItem(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column(
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
            .padding(horizontal = 16.dp),
    ) {
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = title,
            color = Gray70,
            style = PillinTimeTheme.typography.body2Medium
        )
        CustomTextField(
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp),
            state = true,
            value = value,
            onValueChange = onValueChange
        )
    }
}