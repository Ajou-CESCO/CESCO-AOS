package com.example.pillinTimeAndroid.data.remote.dto

data class ScheduleDTO (
    val id: Int,
    val memberId: Int,
    val medicineId: String,
    val medicineName: String,
    val weekday: Int,
    val time: String,
    val startAt: String,
    val endAt: String
)