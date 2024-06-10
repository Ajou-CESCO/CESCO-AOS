package com.whdaud.pillinTimeAndroid.data.remote.dto.request

data class PaymentRequest (
    val payType: String,
    val amount: Int,
    val orderName: String,
    val memberId: Int
)