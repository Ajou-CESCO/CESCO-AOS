package com.example.pillinTimeAndroid.presentation.mypage.relation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillinTimeAndroid.data.remote.dto.request.RelationReqRequest
import com.example.pillinTimeAndroid.domain.repository.RelationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RelationManageViewModel @Inject constructor(
    private val relationRepository: RelationRepository
) : ViewModel() {
    private var phone = mutableStateOf("")

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