package com.example.pillinTimeAndroid.presentation.mypage.editinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.presentation.common.CustomTextField
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme

@Composable
fun EditInfoBefore(
    name: String,
    phone: String,
    ssn: String
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp),
    ) {
        EditInfoBeforeItem("성명", name)
        EditInfoBeforeItem("휴대폰 번호", phone)
        EditInfoBeforeItem("주민등록번호", ssn)
    }
}

@Composable
fun EditInfoBeforeItem(
    title: String,
    value: String
) {
    Row (
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
            .padding(vertical = 17.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Left
    ){
        Text(
            modifier = Modifier
                .width(120.dp)
                .padding(horizontal = 16.dp),
            text = title,
            color = Gray70,
            style = PillinTimeTheme.typography.body2Medium
        )
        Text(
            text = value,
            color = Gray90,
            style = PillinTimeTheme.typography.headline5Medium
        )
    }
}

@Composable
fun EditInfoAfter(
    name: String,
    phone: String,
    ssn: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
    ) {
        EditInfoAfterItem("성명", name, onValueChange)
        EditInfoAfterItem("휴대폰 번호", phone, onValueChange)
        EditInfoAfterItem("주민등록번호", ssn, onValueChange)
    }
}

@Composable
fun EditInfoAfterItem(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column (
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