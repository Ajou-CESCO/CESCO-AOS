package com.example.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.pillinTimeAndroid.presentation.Dimens.AlertDialogPadding
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.ui.theme.Error90
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextFieldDialog(
    modifier: Modifier = Modifier,
    onRequestRelation: () -> Unit,
    onDismiss: () -> Unit,
    textField: @Composable () -> Unit,
    isValidInput: Boolean,
    buttonState: Boolean
) {
    val scope = rememberCoroutineScope()
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
                    .padding(top = 29.dp, bottom = 15.dp),
                text = "추가할 피보호자의\n휴대폰 번호를 입력해주세요",
                style = PillinTimeTheme.typography.headline4Bold,
                color = Gray90,
                lineHeight = 35.sp
            )
            textField()
            if(!isValidInput) {
                Text(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = AlertDialogPadding),
                    text = "휴대폰 번호 형식이 올바르지 않습니다.",
                    style = PillinTimeTheme.typography.body1Regular,
                    color = Error90,
                )
            }
            Row(
                modifier = Modifier.padding(
                    horizontal = AlertDialogPadding,
                    vertical = 20.dp
                ),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                CustomButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 7.dp),
                    enabled = true,
                    filled = ButtonColor.BLANK,
                    size = ButtonSize.MEDIUM,
                    text = "취소하기",
                    onClick = onDismiss
                )
                CustomButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 7.dp),
                    enabled = buttonState,
                    filled = ButtonColor.FILLED,
                    size = ButtonSize.MEDIUM,
                    text = "요청하기",
                    onClick = {
                        scope.launch{
                            onRequestRelation()
                            onDismiss ()
                        }
                    }
                )
            }
        }
    }
}

