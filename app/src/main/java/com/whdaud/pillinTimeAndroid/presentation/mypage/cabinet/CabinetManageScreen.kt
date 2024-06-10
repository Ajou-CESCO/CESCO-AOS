package com.whdaud.pillinTimeAndroid.presentation.mypage.cabinet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.whdaud.pillinTimeAndroid.presentation.common.ButtonColor
import com.whdaud.pillinTimeAndroid.presentation.common.ButtonSize
import com.whdaud.pillinTimeAndroid.presentation.common.CustomButton
import com.whdaud.pillinTimeAndroid.presentation.common.CustomTopBar
import com.whdaud.pillinTimeAndroid.ui.theme.Gray60
import com.whdaud.pillinTimeAndroid.ui.theme.Gray90
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme

@Composable
fun CabinetManageScreen(
    navController: NavController,
    memberId: Int?,
    onRegisterClick: () -> Unit,
) {
    val hasCase = false
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomTopBar(
            title = "연결된 기기",
            showBackButton = true,
            onBackClicked = { navController.popBackStack() },
        )
        if (hasCase) {
            Column(
                modifier = Modifier
                    .padding(top = 43.dp, bottom = 30.dp)
            ) {

            }

        } else {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "등록된 기기가 없어요",
                    color = Gray90,
                    style = PillinTimeTheme.typography.headline5Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "약통을 연동하여 복약 일정을 등록해보세요",
                    color = Gray60,
                    style = PillinTimeTheme.typography.body1Medium
                )
                Spacer(modifier = Modifier.height(32.dp))
                CustomButton(
                    modifier = Modifier.height(48.dp),
                    enabled = true,
                    filled = ButtonColor.FILLED,
                    size = ButtonSize.SMALL,
                    text = "기기 등록하기",
                    onClick = onRegisterClick
                )
            }
        }
    }
}