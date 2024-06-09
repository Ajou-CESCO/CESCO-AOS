package com.whdaud.pillinTimeAndroid.presentation.mypage.relation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whdaud.pillinTimeAndroid.R
import com.whdaud.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.whdaud.pillinTimeAndroid.presentation.common.CustomSwipeCard
import com.whdaud.pillinTimeAndroid.presentation.common.CustomTextField
import com.whdaud.pillinTimeAndroid.presentation.common.CustomTextFieldDialog
import com.whdaud.pillinTimeAndroid.presentation.common.CustomTopBar
import com.whdaud.pillinTimeAndroid.presentation.common.InputType
import com.whdaud.pillinTimeAndroid.presentation.main.MainViewModel
import com.whdaud.pillinTimeAndroid.ui.theme.Gray60
import com.whdaud.pillinTimeAndroid.ui.theme.Gray90
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.ui.theme.White
import com.whdaud.pillinTimeAndroid.util.PhoneVisualTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelationManageScreen(
    navController: NavController,
    title: String?,
    viewModel: RelationManageViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val userDetail = mainViewModel.userDetails.collectAsState()
    val relationList by mainViewModel.relationInfoList.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val showBottomSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val showToast = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val selectedUser = remember { mutableStateOf("") }

    Column {
        CustomTopBar(
            title = title.toString(),
            showBackButton = true,
            onBackClicked = { navController.popBackStack() },
        )
        Spacer(modifier = Modifier.height(37.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = BasicPadding),
                text = "총 ${relationList.size}명",
                color = Gray90,
                style = PillinTimeTheme.typography.headline5Bold
            )
            if(userDetail.value?.isManager == true) {
                Icon(
                    modifier = Modifier
                        .padding(end = BasicPadding)
                        .clickable(
                            onClick = { showDialog.value = true },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    painter = painterResource(id = R.drawable.ic_add_relation),
                    contentDescription = null,
                    tint = Gray60
                )
            } else {
                Icon(
                    modifier = Modifier
                        .padding(end = BasicPadding)
                        .clickable(
                            onClick = { showBottomSheet.value = true },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    painter = painterResource(id = R.drawable.ic_add_relation),
                    contentDescription = null,
                    tint = Gray60
                )
            }
            if (showDialog.value) {
                CustomTextFieldDialog(
                    onRequestRelation = { viewModel.postRelationReq() },
                    onDismiss = { showDialog.value = false },
                    textField = {
                        CustomTextField(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            state = viewModel.isValidateInput(),
                            value = viewModel.getCurrentInput(),
                            onValueChange = viewModel::updateInput,
                            trailIcon = R.drawable.ic_cancel,
                            visualTransformation = PhoneVisualTransformation(),
                            inputType = InputType.PHONE
                        )
                    },
                    buttonState = viewModel.isValidateInput() && viewModel.getCurrentInput()
                        .isNotEmpty(),
                    isValidInput = viewModel.isValidateInput()
                )
            }
            if (showBottomSheet.value) {
                ModalBottomSheet(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = BasicPadding),
                    onDismissRequest = {
                        showBottomSheet.value = false
                    },
                    sheetState = sheetState,
                    containerColor = White
                ) {

                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        CustomSwipeCard(
            isManager = userDetail.value?.isManager,
            onRemove = { relation ->
                scope.launch {
                    selectedUser.value = relation.memberName
                    showToast.value = true
                    val deleteRelationJob = withContext(Dispatchers.Main) {
                        viewModel.deleteRelation(relation.id)
                    }
                    deleteRelationJob.join()
                    mainViewModel.getRelationship()
                }
            },
            onDisconnect = { cabinetId ->
                scope.launch {
                    val deleteCabinetJob = withContext(Dispatchers.Main) {
                        viewModel.deleteCabinet(cabinetId)
                    }
                    deleteCabinetJob.join()
                    mainViewModel.getRelationship()
                }
            },
        )
        Spacer(modifier = Modifier.weight(1f))
        if(showToast.value) {
//            CustomToast(
//                text = "${selectedUser.value}님과의 보호관계를 삭제했어요"
//            ) {
//                showToast.value = false
//            }
        }
    }
}