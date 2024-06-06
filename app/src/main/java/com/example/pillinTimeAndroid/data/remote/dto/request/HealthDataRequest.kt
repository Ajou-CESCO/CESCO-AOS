package com.example.pillinTimeAndroid.data.remote.dto.request

data class HealthDataRequest(
    val steps: Int,
    val sleepTime: Int,
    val calorie: Int,
    val heartRate: Int
)