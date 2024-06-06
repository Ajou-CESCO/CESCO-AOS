package com.example.pillinTimeAndroid.presentation.mypage.relation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillinTimeAndroid.data.remote.dto.request.RelationReqRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.RelationReqResponse
import com.example.pillinTimeAndroid.domain.repository.RelationRepository
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RelationManageViewModel @Inject constructor(
    private val relationRepository: RelationRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private var phone = mutableStateOf("")
    val showToast = mutableStateOf(false)
    private val _managerRequest = MutableStateFlow<List<RelationReqResponse>>(emptyList())

    val managerRequest: StateFlow<List<RelationReqResponse>> = _managerRequest.asStateFlow()

    fun postRelationReq() {
        val formattedPhone = "${phone.value.substring(0, 3)}-${phone.value.substring(3, 7)}-${phone.value.substring(7, 11)}"
        viewModelScope.launch {
            val request = RelationReqRequest(receiverPhone = formattedPhone)
            val result = relationRepository.postRelationRequest(request)
            result.onSuccess { response ->
                Log.e("RelationManageViewModel", "succeed to call api ${response.message}")
            }.onFailure {
                Log.e("RelationManageViewModel", "failed to call api ${it.message}")
            }
            phone.value = ""
        }
    }

    fun getManagerRequest() {
        viewModelScope.launch {
            val response = relationRepository.getRelationRequest()
            response.onSuccess { requestResponse ->
                _managerRequest.value = requestResponse.result
            }.onFailure { requestResponse ->
                Log.e("fetch Manager Request", "Failed to fetch requests: ${requestResponse.message}")
            }
        }
    }
    fun deleteRelation(relationId: Int) {
        viewModelScope.launch {
            val result = relationRepository.deleteRelation(relationId)
            Log.d("Relation Delete", "보호 관계 id: $relationId")
            result.onSuccess { response ->
                Log.e("RelationManageViewModel", "succeed to delete relation ${response.message}")
            }.onFailure {
                Log.e("RelationManageViewModel", "failed to delete relation ${it.message}")
            }
        }
    }

    fun deleteCabinet(cabinetId: Int) {
        viewModelScope.launch {
            val result = userRepository.deleteCabinet(cabinetId)
            result.onSuccess {
                Log.d("deleteCabinet", "Succeeded to delete Cabinet: ${it.result}")
                showToast.value = true
            }.onFailure {
                Log.e("deleteCabinet", "Failed to delete Cabinet: ${it.message}")
            }
        }
    }

    fun getCurrentInput(): String {
        return phone.value
    }

    fun updateInput(input: String) {
        phone.value = input
    }

    fun isValidateInput(): Boolean {
        return phone.value.matches(Regex("^01[0-1,7]-?[0-9]{4}-?[0-9]{4}$")) || phone.value.isEmpty()
    }
}