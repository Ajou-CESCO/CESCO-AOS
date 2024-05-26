package com.example.pillinTimeAndroid.presentation.mypage.relation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.presentation.common.CustomSwipeCard
import com.example.pillinTimeAndroid.presentation.common.CustomTextField
import com.example.pillinTimeAndroid.presentation.common.CustomTextFieldDialog
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.presentation.main.MainViewModel
import com.example.pillinTimeAndroid.ui.theme.Gray60
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme

@Composable
fun RelationManageScreen(
    navController: NavController,
    viewModel: RelationManageViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val userDetail = mainViewModel.userDetails.collectAsState()
    val relationList = mainViewModel.relationInfoList.collectAsState()
    val showDialog = remember { mutableStateOf(false) }

    Column(

    ) {
        CustomTopBar(
            title = "피보호자 관리",
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
                text = "총 ${relationList.value.size}명",
                color = Gray90,
                style = PillinTimeTheme.typography.headline5Bold
            )
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
            if (showDialog.value) {
                CustomTextFieldDialog(
                    onRequestRelation = { viewModel.postRelationReq() },
                    onDismiss = { showDialog.value = false },
                    textField = {
                        CustomTextField(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            state = viewModel.isValidateInput(),
                            value = viewModel.getCurrentInput(),
                            onValueChange = viewModel::updateInput
                        )
                    },
                    buttonState = viewModel.isValidateInput() && viewModel.getCurrentInput().isNotEmpty(),
                    isValidInput = viewModel.isValidateInput()
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        CustomSwipeCard(
            isManager = userDetail.value?.isManager,
            relationList = relationList.value,
            onRemove = {
                relation -> viewModel.deleteRelation(relation.id)
                mainViewModel.getRelationship()
            }
        )
    }
}