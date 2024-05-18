package com.example.pillinTimeAndroid.data.remote.dto.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignInRequest(
    val name: String,
    val phone: String,
    val ssn: String
): Parcelable
