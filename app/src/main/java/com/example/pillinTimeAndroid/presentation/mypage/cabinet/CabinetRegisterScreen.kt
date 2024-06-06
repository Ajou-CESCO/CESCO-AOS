package com.example.pillinTimeAndroid.presentation.mypage.cabinet

import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.pillinTimeAndroid.ui.theme.Gray40
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CabinetRegisterScreen(
    viewModel: CabinetViewModel = hiltViewModel(),
    navController: NavHostController,
    ownerId: Int?,
) {
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val isValidInput = viewModel.isValidateInput()
    val showCamera = remember { mutableStateOf(false) }

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
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable(
                        onClick = { showCamera.value = true },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                text = "QR코드로 등록하기",
                color = Gray40,
                style = PillinTimeTheme.typography.body1Regular
            )
        },
        button = {
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.isValidateInput() && viewModel.getCurrentInput().isNotEmpty(),
                filled = ButtonColor.FILLED,
                size = ButtonSize.MEDIUM,
                text = "확인",
                onClick = {
                    if (ownerId != null) {
                        viewModel.postRegisterCabinet(ownerId, navController = navController)
                    }
                }
            )
        }
    )
    if(showCamera.value) {
        QrCodeReaderScreen(
            cameraPermissionState = cameraPermissionState,
            onDismiss = { showCamera.value = false },
            onQrCodeScanned = { qrCode ->
                viewModel.updateInput(qrCode)
                showCamera.value = false
            }
        )
    }
}
