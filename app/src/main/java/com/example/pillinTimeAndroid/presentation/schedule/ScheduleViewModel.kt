package com.example.pillinTimeAndroid.presentation.schedule

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(

) : ViewModel() {
    val userList = listOf("김종명", "노수인", "심재민", "이재현", "김학준", "김종명", "노수인", "심재민")

}