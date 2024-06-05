package com.example.pillinTimeAndroid.presentation.signin.components

data class SignInPageList(
    val title: String,
    val subtitle: String,
    val hint: String,
    val error: String
)

val signInPages = listOf(
    SignInPageList(
        title = "휴대폰 본인인증",
        subtitle = "본인 명의의 휴대폰 번호를 입력해주세요.",
        hint = "휴대폰 번호 입력",
        error = "휴대폰 번호 형식이 올바르지 않습니다."
    ),
    SignInPageList(
        title = "문자로 발송된\n인증번호를 입력해 주세요",
        subtitle = "",
        hint = "인증번호 입력 (ex. 000000)",
        error = "인증에 실패했어요. 인증번호를 다시 확인해주세요."
    ),
    SignInPageList(
        title = "이름은 무엇인가요?",
        subtitle = "신분증에 표기되어 있는 실명을 입력해주세요.",
        hint = "본명 입력 (ex. 홍길동)",
        error = "이름 형식이 올바르지 않습니다."
    ),
    SignInPageList(
        title = "{name}님의 주민등록번호를\n입력해주세요",
        subtitle = "",
        hint = "주민등록번호 입력",
        error = "주민등록번호 형식이 올바르지 않습니다."
    )
)