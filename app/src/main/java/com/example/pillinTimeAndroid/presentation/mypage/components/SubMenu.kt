package com.example.pillinTimeAndroid.presentation.mypage.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.ui.theme.Gray50
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme

@Composable
fun SubMenu(
    isManager: Boolean,
    onItemClick: (destination: String) -> Unit
) {
    val manage = if (isManager) "피보호자 관리" else "보호자 관리"
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        SubMenuItem(R.drawable.ic_manage, manage) {
            onItemClick("relationManageScreen")
        }
        SubMenuItem(R.drawable.ic_manage, "연결된 기기") {
            onItemClick("deviceManageScreen")
        }
        SubMenuItem(R.drawable.ic_calendar, "복약 일정 관리") {
            onItemClick("doseScheduleManageScreen")
        }
    }
}

@Composable
fun SubMenuItem(
    @DrawableRes icon: Int,
    title: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(shape = CircleShape)
                .border(1.dp, Gray50, CircleShape)
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .clickable(
                        onClick = onClick,
//                        indication = null,
//                        interactionSource = remember { MutableInteractionSource() }
                    ),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            color = Gray90,
            style = PillinTimeTheme.typography.body2Regular
        )
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun SubMenuPreview() {
    PillinTimeAndroidTheme {
//        SubMenu(isManager = true)
    }
}