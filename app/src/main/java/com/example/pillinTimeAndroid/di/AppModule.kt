package com.example.pillinTimeAndroid.di

import android.content.Context
import com.example.pillinTimeAndroid.BuildConfig.BASE_URL
import com.example.pillinTimeAndroid.data.local.HealthConnectManager
import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.data.remote.CabinetService
import com.example.pillinTimeAndroid.data.remote.HealthService
import com.example.pillinTimeAndroid.data.remote.UserService
import com.example.pillinTimeAndroid.data.repository.UserRepositoryImpl
import com.example.pillinTimeAndroid.domain.repository.TokenRepository
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
    fun provideLocalHealthConnectManager(@ApplicationContext context: Context): HealthConnectManager {
        return HealthConnectManager(context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(tokenRepository: TokenRepository, userService: UserService, cabinetService: CabinetService, healthService: HealthService): UserRepository {
        return UserRepositoryImpl(tokenRepository, userService, cabinetService, healthService)
    }

//    @Provides
//    @Singleton
//    fun provideMainViewModel(
//        readUserSession: ReadUserSession,
//        userRepository: UserRepository,
//        medicineRepository: MedicineRepository,
//        relationRepository: RelationRepository,
//        fcmRepository: FcmRepository,
//        localUserDataSource: LocalUserDataSource
//    ): MainViewModel {
//        return MainViewModel(readUserSession, userRepository, medicineRepository, relationRepository, fcmRepository, localUserDataSource)
//    }
}