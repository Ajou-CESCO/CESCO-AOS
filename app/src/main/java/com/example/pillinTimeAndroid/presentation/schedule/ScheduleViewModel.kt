package com.example.pillinTimeAndroid.presentation.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillinTimeAndroid.data.remote.dto.request.FcmPushNotificationRequest
import com.example.pillinTimeAndroid.domain.repository.FcmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val fcmRepository: FcmRepository
) : ViewModel() {

    fun postFcmPushNotification(memberId: Int) {
        viewModelScope.launch {
            val request = FcmPushNotificationRequest(memberId)
            val result = fcmRepository.postFcmPushNotification(request)
            result.onSuccess {
                Log.e("success", "successed notification")
            }.onFailure {
                Log.e("faile", "failed notification")
            }
        }
    }
}