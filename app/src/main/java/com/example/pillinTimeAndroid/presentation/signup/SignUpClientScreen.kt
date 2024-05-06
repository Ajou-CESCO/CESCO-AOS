package com.example.pillinTimeAndroid.presentation.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.presentation.common.GeneralScreen
import com.example.pillinTimeAndroid.presentation.signup.components.ManagerRequestList
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme

@Composable
fun SignUpClientScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController
) {
    val currentPage = viewModel.getCurrentPage()
    val currentPageTitle = viewModel.getCurrentPageTitle()
    val managerRequest by viewModel.managerRequest.collectAsState()
    GeneralScreen(
        topBar = {
            CustomTopBar()
        },
        title = currentPageTitle,
        subtitle = currentPage.subtitle.toString(),
        content = {
            // TODO: API call
            if (managerRequest.isNotEmpty()) {
                ManagerRequestList(
                    managers = managerRequest,
                    onConfirm = {}
                )
            } else {
                
            }
        }
    ) {}
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun SignUpClientScreenPreview() {
    PillinTimeAndroidTheme {
//        SignUpClientScreen(SignUpViewModel(), rememberNavController())
    }
}