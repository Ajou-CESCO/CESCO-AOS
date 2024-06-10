package com.whdaud.pillinTimeAndroid.util

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.LocalDate
import java.util.Calendar

fun getWeeksOfMonth(year: Int, month: Int, day: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(year, month-1, day)
    return calendar.get(Calendar.WEEK_OF_MONTH)
}

fun calendarTitle(year: Int, month: Int): String {
    return "${year}년 ${month.toString().padStart(2,'0')}월"
}

fun getTodayInMillis(): Long {
    val calendar = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, + 1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.timeInMillis
}

// Date Picker selectable dates
@OptIn(ExperimentalMaterial3Api::class)
object PresentOrFutureSelectableDates: SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= getTodayInMillis()
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year >= LocalDate.now().year
    }
}