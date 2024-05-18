package com.example.pillinTimeAndroid.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class UserDTO<T>(
    val memberId: Int,
    val name: String,
    val ssn: String,
    val phone: String,
    val cabinetId: Int,
    val isManager: Boolean,
    val isSubscriber: Boolean,
    val relationList: @RawValue T
) : Parcelable