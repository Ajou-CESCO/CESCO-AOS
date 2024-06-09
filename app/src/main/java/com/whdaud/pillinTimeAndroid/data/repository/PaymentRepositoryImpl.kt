package com.whdaud.pillinTimeAndroid.data.repository

import com.whdaud.pillinTimeAndroid.data.remote.PaymentService
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.PaymentRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.PaymentResponse
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import com.whdaud.pillinTimeAndroid.domain.repository.PaymentRepository
import com.whdaud.pillinTimeAndroid.domain.repository.TokenRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val paymentService: PaymentService,
    private val tokenRepository: TokenRepository
) : PaymentRepository {
    override suspend fun postPaymentInfo(paymentRequest: PaymentRequest): Result<BaseResponse<PaymentResponse>> {
        val accessToken = tokenRepository.loadAccessToken().firstOrNull().orEmpty()
        return try {
            val response = paymentService.postPaymentInfo("Bearer $accessToken", paymentRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSuccess(
        paymentKey: String,
        orderId: String,
        amount: Int
    ): Result<BaseResponse<Any>> {
        return try {
            val response = paymentService.getSuccess(paymentKey, orderId, amount)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}