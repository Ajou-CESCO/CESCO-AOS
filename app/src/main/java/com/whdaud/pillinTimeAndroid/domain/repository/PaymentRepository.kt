package com.whdaud.pillinTimeAndroid.domain.repository

import com.whdaud.pillinTimeAndroid.data.remote.dto.request.PaymentRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.PaymentResponse
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse

interface PaymentRepository {
    suspend fun postPaymentInfo(paymentRequest: PaymentRequest): Result<BaseResponse<PaymentResponse>>
    suspend fun getSuccess(paymentKey: String, orderId: String, amount: Int): Result<BaseResponse<Any>>
}