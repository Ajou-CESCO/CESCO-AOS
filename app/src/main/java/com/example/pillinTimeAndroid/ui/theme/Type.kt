package com.example.pillinTimeAndroid.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.pillinTimeAndroid.R

private val OAGothic = FontFamily(
    Font(R.font.oagothic_extrabold, FontWeight.ExtraBold),
    Font(R.font.oagothic_medium, FontWeight.Medium)
)

private val Pretendard = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_regular, FontWeight.Normal)
)

internal val Typography = PillinTimeTypography(
    logo1Medium = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
    ),
    logo1Extra = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 36.sp,
    ),
    logo2Medium = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    ),
    logo2Extra = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
    ),
    logo3Medium = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
    ),
    logo3Extra = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
    ),
    logo4Medium = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,
    ),
    logo4Extra = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,
    ),
    logo5Medium = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 12.sp,
    ),
    logo5Extra = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 12.sp,
    ),
    headline1Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
    ),
    headline1Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 48.sp,
    ),
    headline1Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
    ),
    headline2Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
    ),
    headline2Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 34.sp,
    ),
    headline2Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
    ),
    headline3Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
    ),
    headline3Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
    ),
    headline3Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
    ),
    headline4Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
    ),
    headline4Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    ),
    headline4Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    headline5Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    headline5Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
    ),
    headline5Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    body1Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    body1Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    ),
    body1Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    body2Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    body2Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
    body2Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    caption1Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    caption1Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
    ),
    caption1Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
    ),
    caption2Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
    ),
    caption2Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
    ),
    caption2Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
    ),
)

@Immutable
data class PillinTimeTypography(
    val logo1Medium: TextStyle,
    val logo1Extra: TextStyle,
    val logo2Medium: TextStyle,
    val logo2Extra: TextStyle,
    val logo3Medium: TextStyle,
    val logo3Extra: TextStyle,
    val logo4Medium: TextStyle,
    val logo4Extra: TextStyle,
    val logo5Medium: TextStyle,
    val logo5Extra: TextStyle,
    val headline1Regular: TextStyle,
    val headline1Medium: TextStyle,
    val headline1Bold: TextStyle,
    val headline2Regular: TextStyle,
    val headline2Medium: TextStyle,
    val headline2Bold: TextStyle,
    val headline3Regular: TextStyle,
    val headline3Medium: TextStyle,
    val headline3Bold: TextStyle,
    val headline4Regular: TextStyle,
    val headline4Medium: TextStyle,
    val headline4Bold: TextStyle,
    val headline5Regular: TextStyle,
    val headline5Medium: TextStyle,
    val headline5Bold: TextStyle,
    val body1Regular: TextStyle,
    val body1Medium: TextStyle,
    val body1Bold: TextStyle,
    val body2Regular: TextStyle,
    val body2Medium: TextStyle,
    val body2Bold: TextStyle,
    val caption1Regular: TextStyle,
    val caption1Medium: TextStyle,
    val caption1Bold: TextStyle,
    val caption2Regular: TextStyle,
    val caption2Medium: TextStyle,
    val caption2Bold: TextStyle,
)

val LocalPillinTimeTypography = staticCompositionLocalOf {
    PillinTimeTypography(
        logo1Medium = TextStyle.Default,
        logo1Extra = TextStyle.Default,
        logo2Medium = TextStyle.Default,
        logo2Extra = TextStyle.Default,
        logo3Medium = TextStyle.Default,
        logo3Extra = TextStyle.Default,
        logo4Medium = TextStyle.Default,
        logo4Extra = TextStyle.Default,
        logo5Medium = TextStyle.Default,
        logo5Extra = TextStyle.Default,
        headline1Regular = TextStyle.Default,
        headline1Medium = TextStyle.Default,
        headline1Bold = TextStyle.Default,
        headline2Regular = TextStyle.Default,
        headline2Medium = TextStyle.Default,
        headline2Bold = TextStyle.Default,
        headline3Regular = TextStyle.Default,
        headline3Medium = TextStyle.Default,
        headline3Bold = TextStyle.Default,
        headline4Regular = TextStyle.Default,
        headline4Medium = TextStyle.Default,
        headline4Bold = TextStyle.Default,
        headline5Regular = TextStyle.Default,
        headline5Medium = TextStyle.Default,
        headline5Bold = TextStyle.Default,
        body1Regular = TextStyle.Default,
        body1Medium = TextStyle.Default,
        body1Bold = TextStyle.Default,
        body2Regular = TextStyle.Default,
        body2Medium = TextStyle.Default,
        body2Bold = TextStyle.Default,
        caption1Regular = TextStyle.Default,
        caption1Medium = TextStyle.Default,
        caption1Bold = TextStyle.Default,
        caption2Regular = TextStyle.Default,
        caption2Medium = TextStyle.Default,
        caption2Bold = TextStyle.Default,
    )
}