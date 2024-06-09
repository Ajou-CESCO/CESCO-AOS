package com.whdaud.pillinTimeAndroid.presentation.mypage.subscribe

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tosspayments.paymentsdk.model.TossPaymentResult
import com.whdaud.pillinTimeAndroid.presentation.Dimens
import com.whdaud.pillinTimeAndroid.presentation.common.ButtonColor
import com.whdaud.pillinTimeAndroid.presentation.common.ButtonSize
import com.whdaud.pillinTimeAndroid.presentation.common.CustomButton
import com.whdaud.pillinTimeAndroid.presentation.common.CustomTopBar
import com.whdaud.pillinTimeAndroid.presentation.main.MainViewModel
import com.whdaud.pillinTimeAndroid.ui.theme.Gray100
import com.whdaud.pillinTimeAndroid.ui.theme.Gray70
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.ui.theme.White

@Composable
fun SubscribeScreen(
    navController: NavController,
    viewModel: SubscribeViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val userDetails by mainViewModel.userDetails.collectAsState()
    val context = LocalContext.current as Activity
    val paymentResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == 200) {
            result.data?.let { data ->
                val extras = data.extras
                if (extras != null) {
                    for (key in extras.keySet()) {
                        val value = extras.get(key)
                        Log.e("payment", "Key: $key Value: $value")
                        if (key == "extraPaymentResultSuccess" && value is TossPaymentResult.Success) {
                            Log.e("payment", "Payment Successful: $value")
                            val successResult = value as TossPaymentResult.Success
                            Log.e("payment", "Order ID: ${successResult.orderId}")
                            Log.e("payment", "Payment Key: ${successResult.paymentKey}")
                            Log.e("payment", "Amount: ${successResult.amount}")
                            viewModel.getSuccess(
                                successResult.paymentKey,
                                successResult.orderId,
                                successResult.amount.toInt()
                            )
                        }
                    }
                }
            }
            Log.e("payment", "success ${result.resultCode}, ${result.data}")
            // 결제가 취소되었어요
        } else {
            Log.e("payment", "failed: ${result.resultCode} ${result.data}")
            // 결제가 실패하였어요
        }
    }
    Column(
        modifier = Modifier
            .background(White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTopBar(
            showBackButton = true,
            onBackClicked = { navController.popBackStack() },
            title = "프리미엄 결제"
        )
        Spacer(modifier = Modifier.weight(.2f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimens.BasicPadding)
        ) {
            Text(
                modifier = Modifier.align(alignment = Alignment.Start),
                text = "프리미엄 이용권 결제",
                color = Gray100,
                style = PillinTimeTheme.typography.logo2Extra
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "해당 이용권을 최초 1회 결제하고 나면 피보호자를 제한 없이\n등록할 수 있어요.\n\n가격: 30,000원",
                color = Gray70,
                style = PillinTimeTheme.typography.body2Regular
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = userDetails?.isSubscriber == false,
            filled = ButtonColor.FILLED,
            size = ButtonSize.MEDIUM,
            text = "결제하기",
            onClick = { viewModel.postPayment(context, paymentResultLauncher) }
        )
    }
}