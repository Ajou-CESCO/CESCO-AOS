package com.example.pillinTimeAndroid.presentation.mypage.cabinet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.common.ButtonColor
import com.example.pillinTimeAndroid.presentation.common.ButtonSize
import com.example.pillinTimeAndroid.presentation.common.CustomButton
import com.example.pillinTimeAndroid.presentation.common.CustomTextField
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.presentation.common.GeneralScreen
import com.example.pillinTimeAndroid.presentation.common.InputType
import com.example.pillinTimeAndroid.ui.theme.Error90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme

@Composable
fun CabinetRegisterScreen(
    viewModel: CabinetViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val isValidInput = viewModel.isValidateInput()
    GeneralScreen(
        topBar = {
            CustomTopBar(
                showBackButton = true,
                onBackClicked = { navController.popBackStack() }
            )
        },
        title = "약통의 시리얼 넘버",
        subtitle = "약통에 적힌 시리얼 넘버를 입력해주세요.",
        content = {
            CustomTextField(
                state = isValidInput,
                value = viewModel.getCurrentInput(),
                hint = "16자리 시리얼 넘버 입력",
                onValueChange = viewModel::updateInput,
                trailIcon = R.drawable.ic_cancel,
                inputType = InputType.SERIAL
            )
            if (!isValidInput) {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = "인증에 실패했어요.\n입력한 정보를 다시 확인해주세요.",
                    style = PillinTimeTheme.typography.body1Regular,
                    color = Error90,
                    lineHeight = 26.sp
                )
            }
        },
        button = {
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.isValidateInput() && viewModel.getCurrentInput().isNotEmpty(),
                filled = ButtonColor.FILLED,
                size = ButtonSize.MEDIUM,
                text = "확인",
                onClick = {
                    viewModel.postRegisterCabinet()
                }
            )
        }
    )
}
