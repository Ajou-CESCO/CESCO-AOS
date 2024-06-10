package com.whdaud.pillinTimeAndroid.data.remote.dto

data class ScheduleLogDTO(
    val cabinetIndexList: List<Int>,
    val logList: List<ScheduleLog>
)
data class ScheduleLog(
    val memberId: Int,
    val plannedAt: String,
    val medicineName: String,
    val takenStatus: Int,
    val cabinetIndex: Int,
    val medicineId: Int
)
