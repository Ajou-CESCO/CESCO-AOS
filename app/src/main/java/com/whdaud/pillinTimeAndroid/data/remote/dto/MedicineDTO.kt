package com.whdaud.pillinTimeAndroid.data.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class MedicineDTO(
    val medicineName: String,
    val medicineSeries: String,
    val medicineCode: String,
    val medicineImage: String,
    val medicineEffect: String,
    val useMethod: String,
    val useWarning: String,
    val depositMethod: String,
    val medicineAdverse: @RawValue MedicineAdverse?
) : Parcelable

data class MedicineAdverse(
    @SerializedName("용량주의")
    val dosageCaution: String? = null,
    @SerializedName("특정연령대금기")
    val ageSpecificContraindication: String? = null,
    @SerializedName("노인주의")
    val elderlyCaution: String? = null,
    @SerializedName("투여기간주의")
    val administrationPeriodCaution: String? = null,
    @SerializedName("임부금기")
    val pregnancyContraindication: String? = null,
    @SerializedName("효능군중복")
    val duplicateEfficacyGroup: String? = null
) {
    fun isEmpty(): Boolean {
        return dosageCaution == null &&
            ageSpecificContraindication == null &&
            elderlyCaution == null &&
            administrationPeriodCaution == null &&
            pregnancyContraindication == null &&
            duplicateEfficacyGroup == null
    }
}