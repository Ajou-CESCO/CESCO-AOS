package com.example.pillinTimeAndroid.data.remote.dto

data class ScheduleLogDTO(
    val memberId: Int,
    val plannedAt: String,
    val medicineName: String,
    val takenStatus: Int
)
