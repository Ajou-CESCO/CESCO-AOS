package com.example.pillinTimeAndroid.data.remote.dto.request

data class SignUpRequest(
    val ssn: String,
    val name: String,
    val phone: String,
    val isManager: Boolean
)
