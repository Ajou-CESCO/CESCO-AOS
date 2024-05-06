package com.example.pillinTimeAndroid.di

import com.example.pillinTimeAndroid.data.repository.SignInRepositoryImpl
import com.example.pillinTimeAndroid.data.repository.SignUpRepositoryImpl
import com.example.pillinTimeAndroid.domain.repository.SignInRepository
import com.example.pillinTimeAndroid.domain.repository.SignUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSignInRepository(signInRepositoryImpl: SignInRepositoryImpl): SignInRepository

    @Binds
    @Singleton
    abstract fun bindSignUpRepository(signUpRepositoryImpl: SignUpRepositoryImpl): SignUpRepository
}