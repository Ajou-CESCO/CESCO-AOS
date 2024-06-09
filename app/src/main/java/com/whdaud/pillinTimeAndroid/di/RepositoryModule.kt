package com.whdaud.pillinTimeAndroid.di

import com.whdaud.pillinTimeAndroid.data.repository.FcmRepositoryImpl
import com.whdaud.pillinTimeAndroid.data.repository.MedicineRepositoryImpl
import com.whdaud.pillinTimeAndroid.data.repository.PaymentRepositoryImpl
import com.whdaud.pillinTimeAndroid.data.repository.RelationRepositoryImpl
import com.whdaud.pillinTimeAndroid.data.repository.SignInRepositoryImpl
import com.whdaud.pillinTimeAndroid.data.repository.SignUpRepositoryImpl
import com.whdaud.pillinTimeAndroid.data.repository.TokenRepositoryImpl
import com.whdaud.pillinTimeAndroid.domain.repository.FcmRepository
import com.whdaud.pillinTimeAndroid.domain.repository.MedicineRepository
import com.whdaud.pillinTimeAndroid.domain.repository.PaymentRepository
import com.whdaud.pillinTimeAndroid.domain.repository.RelationRepository
import com.whdaud.pillinTimeAndroid.domain.repository.SignInRepository
import com.whdaud.pillinTimeAndroid.domain.repository.SignUpRepository
import com.whdaud.pillinTimeAndroid.domain.repository.TokenRepository
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

    @Binds
    @Singleton
    abstract fun bindPaymentRepository(paymentRepositoryImpl: PaymentRepositoryImpl): PaymentRepository
}