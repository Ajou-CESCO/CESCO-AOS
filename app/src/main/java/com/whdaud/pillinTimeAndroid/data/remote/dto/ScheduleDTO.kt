package com.whdaud.pillinTimeAndroid.data.remote.dto

data class ScheduleDTO (
    val groupId: Int,
    val memberId: Int,
    val medicineId: String,
    val medicineName: String,
    val medicineAdverse: MedicineAdverse,
    val cabinetIndex: Int,
    val weekdayList: List<Int>,
    val timeList: List<String>,
    val startAt: String,
    val endAt: String
)