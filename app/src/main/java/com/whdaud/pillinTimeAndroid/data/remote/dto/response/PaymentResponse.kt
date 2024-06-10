package com.whdaud.pillinTimeAndroid.data.remote.dto.response

data class PaymentResponse(
    val payType: String,
    val amount: Int,
    val orderId: String,
    val orderName: String,
    val successUrl: String,
    val failUrl: String
)