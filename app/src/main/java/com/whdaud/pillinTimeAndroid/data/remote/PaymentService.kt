package com.whdaud.pillinTimeAndroid.data.remote

import com.whdaud.pillinTimeAndroid.data.remote.dto.request.PaymentRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.PaymentResponse
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PaymentService {
    @POST("/api/payment/toss")
    suspend fun postPaymentInfo(
        @Header("Authorization") accessToken: String,
        @Body paymentRequest: PaymentRequest
    ): BaseResponse<PaymentResponse>

    @GET("/api/payment/toss/success")
    suspend fun getSuccess(
        @Query("paymentKey") paymentKey: String,
        @Query("orderId") orderId: String,
        @Query("amount") amount: Int
    ): BaseResponse<Any>
}