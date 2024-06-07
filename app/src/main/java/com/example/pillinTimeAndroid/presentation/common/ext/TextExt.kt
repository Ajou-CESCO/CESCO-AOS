package com.example.pillinTimeAndroid.presentation.common.ext

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

fun buildStyledText(
    fullText: String,
    targetText: String,
    targetStyle: SpanStyle,
    defaultStyle: TextStyle
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = defaultStyle.toSpanStyle()) {
            val startIndex = fullText.indexOf(targetText)
            if (startIndex != -1) {
                append(fullText.substring(0, startIndex))
                withStyle(style = targetStyle) {
                    append(targetText)
                }
                append(fullText.substring(startIndex + targetText.length))
            } else {
                append(fullText)
            }
        }
    }
}