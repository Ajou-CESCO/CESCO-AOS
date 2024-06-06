package com.example.pillinTimeAndroid.presentation.schedule.components

data class ScheduleOrderList(
    val title: String,
    val subtitle: String
)

val schedulePages = listOf(
    ScheduleOrderList(
        title = "의약품명을 검색해주세요",
        subtitle = "검색 결과를 통해 해당하는 의약품을 선택해주세요"
    ),
    ScheduleOrderList(
        title = "복용 주기가 어떻게 되나요?",
        subtitle = ""
    ),
    ScheduleOrderList(
        title = "복용 주기가 어떻게 되나요?",
        subtitle = ""
    ),
    ScheduleOrderList(
        title = "언제까지 복용하나요?",
        subtitle = "의약품을 복용하는 시작일과 종료일을 선택해주세요"
    ),
    ScheduleOrderList(
        title = "약통 칸을 선택해주세요",
        subtitle = "약을 담을 칸의 색상을 선택해주세요"
    )
)