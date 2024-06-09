package com.whdaud.pillinTimeAndroid.presentation.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.FcmPushNotificationRequest
import com.whdaud.pillinTimeAndroid.domain.repository.FcmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val fcmRepository: FcmRepository
) : ViewModel() {
    fun postFcmPushNotification(memberId: Int, userName: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val request = FcmPushNotificationRequest(memberId)
            val result = fcmRepository.postFcmPushNotification(request)
            result.onSuccess {
                if(it.status == 200) {
                    onResult("${userName}님을 콕 찔렀어요")
                } else {
                    onResult("네트워크 에러 발생 다시 시도해주세요")
                }
                Log.e("success", "successed notification")
            }.onFailure {
                onResult("알 수 없는 오류 발생 다시 시도해주세요")
                Log.e("faile", "failed notification ${it.message}")
            }
        }
    }
}