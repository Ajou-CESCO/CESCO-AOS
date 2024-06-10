package com.whdaud.pillinTimeAndroid.di

import android.content.Context
import com.tosspayments.paymentsdk.TossPayments
import com.whdaud.pillinTimeAndroid.BuildConfig.BASE_URL
import com.whdaud.pillinTimeAndroid.BuildConfig.TOSS_KEY
import com.whdaud.pillinTimeAndroid.data.local.HealthConnectManager
import com.whdaud.pillinTimeAndroid.data.local.LocalUserDataSource
import com.whdaud.pillinTimeAndroid.data.remote.CabinetService
import com.whdaud.pillinTimeAndroid.data.remote.HealthService
import com.whdaud.pillinTimeAndroid.data.remote.UserService
import com.whdaud.pillinTimeAndroid.data.repository.UserRepositoryImpl
import com.whdaud.pillinTimeAndroid.domain.repository.TokenRepository
import com.whdaud.pillinTimeAndroid.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    @Provides
    @Singleton
    fun provideApiInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
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
    fun provideLocalHealthConnectManager(@ApplicationContext context: Context): HealthConnectManager {
        return HealthConnectManager(context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(tokenRepository: TokenRepository, userService: UserService, cabinetService: CabinetService, healthService: HealthService): UserRepository {
        return UserRepositoryImpl(tokenRepository, userService, cabinetService, healthService)
    }

    @Provides
    @Singleton
    fun provideTossPayments(): TossPayments {
        return TossPayments(TOSS_KEY)
    }
}