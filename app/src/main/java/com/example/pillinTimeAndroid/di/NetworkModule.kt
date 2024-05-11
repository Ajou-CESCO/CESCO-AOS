package com.example.pillinTimeAndroid.di

import com.example.pillinTimeAndroid.data.remote.CabinetService
import com.example.pillinTimeAndroid.data.remote.RelationService
import com.example.pillinTimeAndroid.data.remote.SignInService
import com.example.pillinTimeAndroid.data.remote.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSignInService(retrofit: Retrofit): SignInService {
        return retrofit.create(SignInService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideRelationService(retrofit: Retrofit): RelationService {
        return retrofit.create(RelationService::class.java)
    }

    @Provides
    @Singleton
    fun provideCabinetService(retrofit: Retrofit): CabinetService {
        return retrofit.create(CabinetService::class.java)
    }
}