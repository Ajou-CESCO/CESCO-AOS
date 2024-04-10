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
    Logo1_Medium = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
    ),
    Logo1_Extra = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 36.sp,
    ),
    Logo2_Medium = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    ),
    Logo2_Extra = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
    ),
    Logo3_Medium = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
    ),
    Logo3_Extra = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
    ),
    Logo4_Medium = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,
    ),
    Logo4_Extra = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,
    ),
    Logo5_Medium = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 12.sp,
    ),
    Logo5_Extra = TextStyle(
        fontFamily = OAGothic,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 12.sp,
    ),
    Headline1_Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
    ),
    Headline1_Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 48.sp,
    ),
    Headline1_Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
    ),
    Headline2_Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
    ),
    Headline2_Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 34.sp,
    ),
    Headline2_Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
    ),
    Headline3_Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
    ),
    Headline3_Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
    ),
    Headline3_Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
    ),
    Headline4_Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
    ),
    Headline4_Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    ),
    Headline4_Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    Headline5_Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    Headline5_Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
    ),
    Headline5_Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    Body1_Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    Body1_Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    ),
    Body1_Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    Body2_Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    Body2_Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
    Body2_Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    Caption1_Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    Caption1_Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
    ),
    Caption1_Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
    ),
    Caption2_Regular = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
    ),
    Caption2_Medium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
    ),
    Caption2_Bold = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
    ),
)


@Immutable
data class PillinTimeTypography(
    val Logo1_Medium: TextStyle,
    val Logo1_Extra: TextStyle,
    val Logo2_Medium: TextStyle,
    val Logo2_Extra: TextStyle,
    val Logo3_Medium: TextStyle,
    val Logo3_Extra: TextStyle,
    val Logo4_Medium: TextStyle,
    val Logo4_Extra: TextStyle,
    val Logo5_Medium: TextStyle,
    val Logo5_Extra: TextStyle,
    val Headline1_Regular: TextStyle,
    val Headline1_Medium: TextStyle,
    val Headline1_Bold: TextStyle,
    val Headline2_Regular: TextStyle,
    val Headline2_Medium: TextStyle,
    val Headline2_Bold: TextStyle,
    val Headline3_Regular: TextStyle,
    val Headline3_Medium: TextStyle,
    val Headline3_Bold: TextStyle,
    val Headline4_Regular: TextStyle,
    val Headline4_Medium: TextStyle,
    val Headline4_Bold: TextStyle,
    val Headline5_Regular: TextStyle,
    val Headline5_Medium: TextStyle,
    val Headline5_Bold: TextStyle,
    val Body1_Regular: TextStyle,
    val Body1_Medium: TextStyle,
    val Body1_Bold: TextStyle,
    val Body2_Regular: TextStyle,
    val Body2_Medium: TextStyle,
    val Body2_Bold: TextStyle,
    val Caption1_Regular: TextStyle,
    val Caption1_Medium: TextStyle,
    val Caption1_Bold: TextStyle,
    val Caption2_Regular: TextStyle,
    val Caption2_Medium: TextStyle,
    val Caption2_Bold: TextStyle,
)

val LocalPillinTimeTypography = staticCompositionLocalOf {
    PillinTimeTypography(
        Logo1_Medium = TextStyle.Default,
        Logo1_Extra = TextStyle.Default,
        Logo2_Medium = TextStyle.Default,
        Logo2_Extra = TextStyle.Default,
        Logo3_Medium = TextStyle.Default,
        Logo3_Extra = TextStyle.Default,
        Logo4_Medium = TextStyle.Default,
        Logo4_Extra = TextStyle.Default,
        Logo5_Medium = TextStyle.Default,
        Logo5_Extra = TextStyle.Default,
        Headline1_Regular = TextStyle.Default,
        Headline1_Medium = TextStyle.Default,
        Headline1_Bold = TextStyle.Default,
        Headline2_Regular = TextStyle.Default,
        Headline2_Medium = TextStyle.Default,
        Headline2_Bold = TextStyle.Default,
        Headline3_Regular = TextStyle.Default,
        Headline3_Medium = TextStyle.Default,
        Headline3_Bold = TextStyle.Default,
        Headline4_Regular = TextStyle.Default,
        Headline4_Medium = TextStyle.Default,
        Headline4_Bold = TextStyle.Default,
        Headline5_Regular = TextStyle.Default,
        Headline5_Medium = TextStyle.Default,
        Headline5_Bold = TextStyle.Default,
        Body1_Regular = TextStyle.Default,
        Body1_Medium = TextStyle.Default,
        Body1_Bold = TextStyle.Default,
        Body2_Regular = TextStyle.Default,
        Body2_Medium = TextStyle.Default,
        Body2_Bold = TextStyle.Default,
        Caption1_Regular = TextStyle.Default,
        Caption1_Medium = TextStyle.Default,
        Caption1_Bold = TextStyle.Default,
        Caption2_Regular = TextStyle.Default,
        Caption2_Medium = TextStyle.Default,
        Caption2_Bold = TextStyle.Default,
    )
}