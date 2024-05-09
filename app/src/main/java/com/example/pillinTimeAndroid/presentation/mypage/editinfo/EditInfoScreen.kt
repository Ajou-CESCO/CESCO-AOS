package com.example.pillinTimeAndroid.presentation.mypage.editinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.presentation.common.ButtonColor
import com.example.pillinTimeAndroid.presentation.common.ButtonSize
import com.example.pillinTimeAndroid.presentation.common.CustomButton
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.White

@Composable
fun EditInfoScreen(
    viewModel: EditInfoViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val editMode = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val userDetails by viewModel.userDetails.collectAsState()

    val name = remember { mutableStateOf(userDetails?.name ?: "") }
    val phone = remember { mutableStateOf(userDetails?.phone ?: "") }
    val ssn = remember { mutableStateOf(userDetails?.ssn ?: "") }

    // 입력 유효성 검사
    fun isInputValid(): Boolean {
        return name.value.matches(Regex("^[가-힣a-zA-Z]{1,}$")) &&
            phone.value.matches(Regex("^01[0-1,7]-?[0-9]{4}-?[0-9]{4}$")) &&
            ssn.value.matches(Regex("^[0-9]{6}-?[1-4]$"))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .clickable(
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        CustomTopBar(
            onBackClicked = {
                navController.popBackStack()
            },
            showBackButton = true,
            title = "내 정보 관리",
        )
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            modifier = Modifier.padding(start = 33.dp),
            text = "기본 정보",
            color = Gray90,
            style = PillinTimeTheme.typography.headline5Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (editMode.value) {
            EditInfoAfter(
                userDetails?.name.toString(),
                userDetails?.phone.toString(),
                userDetails?.ssn.toString(),
                {}
            )
        } else {
            EditInfoBefore(
                userDetails?.name.toString(),
                userDetails?.phone.toString(),
                userDetails?.ssn.toString()
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(BasicPadding),
            enabled = true,
            filled = ButtonColor.FILLED,
            size = ButtonSize.MEDIUM,
            text = if (editMode.value) "수정완료" else "수정하기",
            onClick = {
                if (editMode.value) {
                    viewModel.patchUserInfo(SignInRequest(name.value, phone.value, ssn.value))
                }
                editMode.value = !editMode.value
            }
        )
    }

}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun EditInfoScreenPreview() {
    PillinTimeAndroidTheme {
        EditInfoScreen(hiltViewModel(), rememberNavController())
    }
}