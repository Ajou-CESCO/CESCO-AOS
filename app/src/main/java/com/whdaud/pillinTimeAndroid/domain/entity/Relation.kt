package com.whdaud.pillinTimeAndroid.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Relation(
    val id: Int,
    val memberId: Int,
    val memberName: String,
    val memberSsn: String,
    val memberPhone: String,
    val cabinetId: Int
) : Parcelable
