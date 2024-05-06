package com.example.pillinTimeAndroid.presentation.signup.components

data class SignUpPageList(
    val title: String,
    val subtitle: String? = "",
    val hint: String? = "",
    val error: String? = ""
)

val clientPages = listOf(
    SignUpPageList(
        title = "{name}님을 케어할 수 있는\n보호자를 기다리고 있어요",
        subtitle = "케어 신청이 올 때까지 기다려 주세요..."
    ),
    SignUpPageList(
        title = "보호자들이\n{name}님을 기다리고 있어요",
        subtitle = "단 한 명의 보호자만 선택할 수 있어요"
    )
)

val managerPages = listOf(
    SignUpPageList(
        title = "피보호자의 휴대폰 번호를\n입력해 주세요",
        subtitle = "주로 사용하는 휴대폰의 번호를 권장해요",
        hint = "휴대폰 번호 입력",
        error = "휴대폰 번호 형식이 올바르지 않습니다."
    )
)