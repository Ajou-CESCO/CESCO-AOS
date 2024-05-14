package com.example.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.pillinTimeAndroid.presentation.Dimens.AlertDialogPadding
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    confirmText: String,
    dismissText: String = "",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    BasicAlertDialog(
        modifier = modifier.padding(horizontal = BasicPadding),
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shapes.medium)
                .background(White)
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = AlertDialogPadding)
                    .padding(top = 29.dp),
                text = title,
                style = PillinTimeTheme.typography.headline4Bold,
                color = Gray90,
                lineHeight = 35.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(horizontal = AlertDialogPadding),
                text = description,
                style = PillinTimeTheme.typography.body2Medium,
                color = Gray70,
                lineHeight = 23.sp
            )
            Row(
                modifier = Modifier.padding(horizontal = AlertDialogPadding, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                if (dismissText.isNotBlank()) {
                    CustomButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 7.dp),
                        enabled = true,
                        filled = ButtonColor.BLANK,
                        size = ButtonSize.MEDIUM,
                        text = dismissText,
                        onClick = onDismiss
                    )
                }
                CustomButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = if (dismissText.isNotBlank()) 7.dp else 0.dp),
                    enabled = true,
                    filled = ButtonColor.FILLED,
                    size = ButtonSize.MEDIUM,
                    text = confirmText,
                    onClick = onConfirm
                )
            }
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true
)
@Composable
private fun CustomAlertDialogPreview() {
    PillinTimeAndroidTheme {
        val shouldShowDialog = remember { mutableStateOf(true) }

        CustomAlertDialog(title = "hi",
            description = "good",
            confirmText = "탈퇴하겠습니다.",
            dismissText = "취소하겠습니다.",
            onConfirm = {},
            onDismiss = { shouldShowDialog.value = false })
    }
}