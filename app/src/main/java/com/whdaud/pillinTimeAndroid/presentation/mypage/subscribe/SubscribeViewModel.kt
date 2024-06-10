package com.whdaud.pillinTimeAndroid.presentation.mypage.subscribe

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosspayments.paymentsdk.TossPayments
import com.tosspayments.paymentsdk.model.paymentinfo.TossCardPaymentInfo
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.PaymentRequest
import com.whdaud.pillinTimeAndroid.domain.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscribeViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val tossPayments: TossPayments
) : ViewModel() {

    fun postPayment(activity: Activity, paymentResultLauncher: ActivityResultLauncher<Intent>) {
        viewModelScope.launch {
            val result = paymentRepository.postPaymentInfo(
                PaymentRequest(
                    payType = "CARD",
                    amount = 30000,
                    orderName = "프리미엄 이용권",
                    memberId = 5
                )
            )

            result.onSuccess { response ->
                if(response.status == 200) {
                    Log.e("TOSS PAYMENTS", "RESULT: ${response.result}")
                    tossPayments.requestCardPayment(
                        activity,
                        TossCardPaymentInfo(
                            orderId = response.result.orderId,
                            orderName = response.result.orderName,
                            amount = response.result.amount.toLong()
                        ),
                        paymentResultLauncher
                    )
                } else {
                    Log.e("TOSS PAYMENTS", "RESULT: ${response.result}")
                }
            }.onFailure {
                Log.e("TOSS PAYMENTS", "RESULT: ${it.message}")
            }
        }
    }

    fun getSuccess(paymentKey: String, orderId: String, amount: Int) {
        viewModelScope.launch {
            val result = paymentRepository.getSuccess(paymentKey, orderId, amount)
            result.onSuccess {
                Log.e("TOSS PAYMENTS Success", "Success RESULT: ${it.status}")
            }.onFailure {
                Log.e("TOSS PAYMENTS Success", "Fail RESULT: ${it.message}")
            }
        }
    }
}