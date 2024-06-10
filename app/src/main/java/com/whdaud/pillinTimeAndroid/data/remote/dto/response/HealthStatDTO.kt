package com.whdaud.pillinTimeAndroid.data.remote.dto.response

data class HealthStatDTO(
    val id: Int,
    val ageGroup: Int,
    val steps: Int,
    val averStep: Int,
    val stepsMessage: String,
    val calorie: Int,
    val calorieMessage: String,
    val heartRate: Int,
    val heartRateMessage: String,
    val sleepTime: Int,
    val recommendSleepTime: Int,
    val sleepTimeMessage: String
)
