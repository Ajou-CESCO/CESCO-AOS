package com.example.pillinTimeAndroid.di

import com.example.pillinTimeAndroid.data.repository.FcmRepositoryImpl
import com.example.pillinTimeAndroid.data.repository.MedicineRepositoryImpl
import com.example.pillinTimeAndroid.data.repository.RelationRepositoryImpl
import com.example.pillinTimeAndroid.data.repository.SignInRepositoryImpl
import com.example.pillinTimeAndroid.data.repository.SignUpRepositoryImpl
import com.example.pillinTimeAndroid.data.repository.TokenRepositoryImpl
import com.example.pillinTimeAndroid.domain.repository.FcmRepository
import com.example.pillinTimeAndroid.domain.repository.MedicineRepository
import com.example.pillinTimeAndroid.domain.repository.RelationRepository
import com.example.pillinTimeAndroid.domain.repository.SignInRepository
import com.example.pillinTimeAndroid.domain.repository.SignUpRepository
import com.example.pillinTimeAndroid.domain.repository.TokenRepository
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

    @Binds
    @Singleton
    abstract fun bindRelationRepository(relationRepositoryImpl: RelationRepositoryImpl): RelationRepository

    @Binds
    @Singleton
    abstract fun bindMedicineRepository(medicineRepositoryImpl: MedicineRepositoryImpl): MedicineRepository

    @Binds
    @Singleton
    abstract fun bindTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl): TokenRepository

    @Binds
    @Singleton
    abstract fun bindFcmRepository(fcmRepositoryImpl: FcmRepositoryImpl): FcmRepository
}