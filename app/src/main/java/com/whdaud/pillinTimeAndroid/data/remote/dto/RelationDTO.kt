package com.whdaud.pillinTimeAndroid.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RelationDTO(
    val id: Int,
    val memberId: Int,
    val memberName: String,
    val memberSsn: String,
    val memberPhone: String,
    val cabinetId: Int
) : Parcelable