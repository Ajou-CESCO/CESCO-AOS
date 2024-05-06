package com.example.pillinTimeAndroid.presentation.signup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary40
import com.example.pillinTimeAndroid.ui.theme.Primary5
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun RoleSelector(
    selectedRole: Int?,
    onRoleSelect: (Int?) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
            .background(Color.Transparent),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf(
            "약 복용 및 건강에 대해 관리받아요" to "피보호자",
            "피보호자의 건강 상태를 관리해요" to "보호자"
        ).forEachIndexed { index, pair ->
            SelectorButton(
                detail = pair.first,
                text = pair.second,
                selected = selectedRole == index,
                onSelect = { isSelected ->
                    onRoleSelect(if (isSelected) null else index)
                }
            )
        }
    }
}

@Composable
fun SelectorButton(
    detail: String,
    text: String,
    selected: Boolean,
    onSelect: (Boolean) -> Unit
) {
    val backgroundColor = if (selected) Primary60 else Primary5
    val detailColor = if (selected) Primary40 else Gray70
    val textColor = if (selected) White else Gray90

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onSelect(selected) },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .background(
                color = backgroundColor,
                shape = shapes.medium
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 17.dp, start = 22.dp),
            text = detail,
            color = detailColor,
            style = PillinTimeTheme.typography.body2Medium
        )
        Text(
            modifier = Modifier.padding(bottom = 17.dp, start = 22.dp),
            text = text,
            color = textColor,
            style = PillinTimeTheme.typography.headline5Bold
        )
    }
}