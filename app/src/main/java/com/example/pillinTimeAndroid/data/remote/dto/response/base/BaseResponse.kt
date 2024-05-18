package com.example.pillinTimeAndroid.data.remote.dto.response.base

data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val result: T
)