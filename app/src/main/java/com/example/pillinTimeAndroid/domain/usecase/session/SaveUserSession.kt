package com.example.pillinTimeAndroid.domain.usecase.session

import com.example.pillinTimeAndroid.domain.entity.User
import com.example.pillinTimeAndroid.domain.repository.UserRepository

class SaveUserSession(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User) {
        userRepository.saveUserSession(user)
    }
}