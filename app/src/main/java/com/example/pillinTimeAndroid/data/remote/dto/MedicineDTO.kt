package com.example.pillinTimeAndroid.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicineDTO(
    val medicineName: String,
    val medicineCode: String,
    val medicineImage: String,
    val medicineEffect: String,
    val useMethod: String,
    val useWarning: String,
    val depositMethod: String
): Parcelable
