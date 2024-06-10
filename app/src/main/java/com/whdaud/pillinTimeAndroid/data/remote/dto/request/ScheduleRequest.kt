package com.whdaud.pillinTimeAndroid.data.remote.dto.request

import com.whdaud.pillinTimeAndroid.data.remote.dto.MedicineAdverse

data class ScheduleRequest (
    val memberId: Int,
    val medicineId: String,
    val medicineName: String,
    val medicineSeries: String,
    val medicineAdverse: MedicineAdverse?,
    val cabinetIndex: Int,
    val weekdayList: List<Int>,
    val timeList: List<String>,
    val startAt: String,
    val endAt: String
)