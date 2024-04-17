package com.example.pillinTimeAndroid.presentation.signin

data class InPage(
    val title: String,
    val subtitle: String,
    val hint: String,
    val error: String
)

val inPages = listOf(
    InPage(
        title = "이름은 무엇인가요?",
        subtitle = "신분증에 표기되어 있는 실명을 입력해주세요.",
        hint = "본명 입력 (ex. 홍길동)",
        error = "이름 형식이 올바르지 않습니다."
    ),
    InPage(
        title = "휴대폰 본인인증",
        subtitle = "본인 명의의 휴대폰 번호를 입력해주세요.",
        hint = "휴대폰 번호 입력",
        error = "휴대폰 번호 형식이 올바르지 않습니다."
    ),
    InPage(
        title = "문자로 발송된\n인증번호를 입력해 주세요",
        subtitle = "",
        hint = "인증번호 입력",
        error = "인증에 실했어요.\n입력한 정보를 다시 확인해주세요."
    )
)