package com.example.pillinTimeAndroid.di

import android.content.Context
import com.example.pillinTimeAndroid.BuildConfig.BASE_URL
import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.data.repository.UserRepositoryImpl
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiInstance(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserLocalDataSource(@ApplicationContext context: Context): LocalUserDataSource {
        return LocalUserDataSource(context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(localDataSource: LocalUserDataSource): UserRepository {
        return UserRepositoryImpl(localDataSource)
    }
}