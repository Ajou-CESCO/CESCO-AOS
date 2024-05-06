package com.example.pillinTimeAndroid.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String? = "",
    val phone: String? = "",
    val ssn: String? = "",
    val accessToken: String? = null,
    val refreshToken: String? = "",
    val userType: Int? = 0
) : Parcelable
