package com.example.pillinTimeAndroid.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TokenDTO(
    @SerializedName ("access_token") val accessToken: String
)

data class FCMTokenDTO(
    val fcmToken: String
)