package com.whdaud.pillinTimeAndroid.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class SsnVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digitsOnly = text.text.filter { it.isDigit() }
        val maskChar = '●'
        val formatted = when {
            digitsOnly.length > 6 -> "${digitsOnly.substring(0, 6)}-${digitsOnly.substring(6).padEnd(7, maskChar)}"
            digitsOnly.length == 6 -> "${digitsOnly.substring(0, 6)}-"
            else -> digitsOnly
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset < 6 -> offset
                    digitsOnly.length == 6 -> offset + 1
                    else -> 7 + (offset - 6).coerceAtLeast(0)
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 7 -> offset
                    digitsOnly.length == 6 -> offset - 1
                    else -> 6 + (offset - 7).coerceAtMost(digitsOnly.length - 6)
                }
            }
        }
        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}