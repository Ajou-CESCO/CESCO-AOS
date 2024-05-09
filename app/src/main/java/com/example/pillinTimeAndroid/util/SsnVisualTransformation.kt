package com.example.pillinTimeAndroid.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class SsnVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digitsOnly = text.text.filter { it.isDigit() }
        val maskChar = '●'
        val formatted = if (digitsOnly.length > 6) {
            "${digitsOnly.substring(0, 6)}-${digitsOnly.substring(6).padEnd(7, maskChar)}"
        } else digitsOnly

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 6) return offset
                return 7 + (offset - 6).coerceAtLeast(0)
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 7) return offset
                return 6 + (offset - 7).coerceAtMost(digitsOnly.length - 6)
            }
        }
        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}