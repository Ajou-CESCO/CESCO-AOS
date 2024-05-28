package com.example.pillinTimeAndroid.data.remote.dto.request

data class ScheduleRequest (
    val memberId: Int,
    val medicineId: String,
    val cabinetIndex: Int,
    val weekdayList: List<Int>,
    val timeList: List<String>,
    val startAt: String,
    val endAt: String
)

data class ScheduleGetRequest (
    val memberId: Int
)