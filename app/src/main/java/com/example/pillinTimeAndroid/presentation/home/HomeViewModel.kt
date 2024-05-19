package com.example.pillinTimeAndroid.presentation.home

import androidx.lifecycle.ViewModel
import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.domain.repository.MedicineRepository
import com.example.pillinTimeAndroid.domain.repository.RelationRepository
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localUserDataSource: LocalUserDataSource,
    private val userRepository: UserRepository,
    private val relationRepository: RelationRepository,
    private val medicineRepository: MedicineRepository,
) : ViewModel() {

    private val selectedIndex = MutableStateFlow(0)
    fun selectUser(index: Int) {
        selectedIndex.value = index
    }
}