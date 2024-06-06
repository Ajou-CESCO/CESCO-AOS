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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.presentation.common.ButtonColor
import com.example.pillinTimeAndroid.presentation.common.ButtonSize
import com.example.pillinTimeAndroid.presentation.common.CustomAlertDialog
import com.example.pillinTimeAndroid.presentation.common.CustomButton
import com.example.pillinTimeAndroid.presentation.common.CustomToast
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.White

@Composable
fun EditInfoScreen(
    viewModel: EditInfoViewModel = hiltViewModel(),
    navController: NavHostController
) {
//    val editMode = remember { mutableStateOf(false) }
//    val focusManager = LocalFocusManager.current
//    val keyboardController = LocalSoftwareKeyboardController.current
    val userDetails by viewModel.userDetails.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val showToast = viewModel.showToast
//    val name = remember { mutableStateOf(userDetails?.name ?: "") }
//    val phone = remember { mutableStateOf(userDetails?.phone ?: "") }
//    val ssn = remember { mutableStateOf(userDetails?.ssn ?: "") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .clickable(
                onClick = {
//                    focusManager.clearFocus()
//                    keyboardController?.hide()
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
//        if (editMode.value) {
//            EditInfoAfter(
//                userDetails?.name.toString(),
//                userDetails?.phone.toString(),
//                userDetails?.ssn.toString(),
//                {}
//            )
//        } else {
//            EditInfoBefore(
//                userDetails?.name.toString(),
//                userDetails?.phone.toString(),
//                userDetails?.ssn.toString()
//            )
//        }
        EditInfoBefore(
            userDetails?.name.toString(),
            userDetails?.phone.toString(),
            userDetails?.ssn.toString()
        )
        Spacer(modifier = Modifier.weight(1f))
        // 약통 해제 버튼
        if (userDetails?.isManager == false) {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(BasicPadding),
                enabled = userDetails?.cabinetId != 0,
                filled = ButtonColor.BLANK,
                size = ButtonSize.MEDIUM,
                text = "약통 해제",
                onClick = {
                    showDialog.value = true
                }
            )
        }
        if (showDialog.value) {
            CustomAlertDialog(
                title = "약통의 연결을 해제하시겠습니까?",
                description = "약통의 연결을 해제하시게 되면 더 이상 복용 스케쥴을\n추가하실 수 없게 됩니다.",
                confirmText = "해제하기",
                dismissText = "취소하기",
                onConfirm = {
                    userDetails?.cabinetId?.let { viewModel.deleteCabinet(it) }
                    showDialog.value = false
                },
                onDismiss = { showDialog.value = false })
        }
        if(showToast.value) {
            CustomToast(text = "약통을 성공적으로 연결 해제하였습니다") {
                showToast.value = false
            }
        }
    }
}