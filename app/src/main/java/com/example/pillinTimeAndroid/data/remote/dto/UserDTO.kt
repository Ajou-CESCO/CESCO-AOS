package com.example.pillinTimeAndroid.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDTO(
    val id: Int,
    val uuid: String,
    val name: String,
    val ssn: String,
     val phone: String,
    val gender: Int,
    val userType: Int,
     val inManager: Boolean,
    val isSubscriber: Boolean,
     val hasCase: Boolean,
) : Parcelable
