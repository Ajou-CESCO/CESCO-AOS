package com.example.pillinTimeAndroid.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digitsOnly = text.text.filter { it.isDigit() }
        val formatted = when {
            digitsOnly.length > 7 ->
                "${digitsOnly.substring(0, 3)}-${digitsOnly.substring(3, 7)}-${digitsOnly.substring(7)}"
            digitsOnly.length > 3 ->
                "${digitsOnly.substring(0, 3)}-${digitsOnly.substring(3)}"

            else -> digitsOnly
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 3 -> offset
                    offset in 4..6 -> offset + 1
                    offset in 8..10 -> offset + 2
                    else -> (offset + 2).coerceAtMost(formatted.length)
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 3 -> offset
                    offset in 4..6 -> offset - 1
                    offset in 8..10 -> offset - 2
                    else -> (offset - 2).coerceAtMost(formatted.length)
                }
            }
        }
        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}