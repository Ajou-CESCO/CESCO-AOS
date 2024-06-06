package com.example.pillinTimeAndroid.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val memberId: Int,
    val name: String,
    val ssn: String,
    val phone: String,
    val cabinetId: Int,
    val isManager: Boolean,
    val isSubscriber: Boolean,
) : Parcelable

data class HomeUser(
    val memberId: Int,
    val name: String,
    val cabinetId: Int,
    val isManager: Boolean
)