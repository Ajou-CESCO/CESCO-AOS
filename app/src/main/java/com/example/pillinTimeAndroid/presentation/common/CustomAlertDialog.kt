package com.example.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pillinTimeAndroid.presentation.Dimens.AlertDialogPadding
import com.example.pillinTimeAndroid.presentation.Dimens.AlertDialogSpacer
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.presentation.Dimens.BasicWidth
import com.example.pillinTimeAndroid.presentation.Dimens.SmallSize
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun CustomAlertDialog(
    modifier: Modifier,
    text: String,
    hint: String
) {
    Column(
        modifier = Modifier
            .padding(BasicPadding)
            .width(BasicWidth)
            .background(White, shape = shapes.medium),
        verticalArrangement = Arrangement.Center,
        ) {
        Text(
            modifier = Modifier
                .padding(horizontal = AlertDialogPadding)
                .padding(top = 29.dp),
            text = text,
            style = PillinTimeTheme.typography.headline4Bold,
            color = Gray90,
            lineHeight = 35.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.padding(horizontal = AlertDialogPadding),
            text = hint,
            style = PillinTimeTheme.typography.body2Medium,
            color = Gray70,
            lineHeight = 23.sp
        )
        Spacer(modifier = Modifier.height(29.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            Arrangement.Center
        ) {
            CustomButton(
                modifier = modifier
                    .weight(1f)
                    .padding(start = AlertDialogPadding)
                    .height(SmallSize),
                enabled = true,
                filled = ButtonColor.BLANK,
                size = ButtonSize.SMALL,
                text = "거절할게요"
            ) {
                /* TODO: Button Action - decline */
            }
            Spacer(modifier = Modifier.width(AlertDialogSpacer))
            CustomButton(
                modifier = modifier
                    .weight(1f)
                    .padding(end = AlertDialogPadding)
                    .height(SmallSize),
                enabled = true,
                filled = ButtonColor.FILLED,
                size = ButtonSize.SMALL,
                text = "수락할게요"
            ) {
                /* TODO: Button Action - accept */
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CustomAlertDialogPreview() {
    PillinTimeAndroidTheme {
        CustomAlertDialog(
            modifier = Modifier.background(Color.White),
            text = "아아아님을 보호자로\n" +
                "수락하시겠어요?",
            hint = "수락을 선택하면 아아아님이 회원님의\n" +
                "약 복용 현황과 건강 상태를 관리할 수 있어요."
        )
    }
}