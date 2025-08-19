package com.transigo.app.core.di

import com.transigo.app.data.repository.DriverRepository
import com.transigo.app.data.repository.AdminBookingRepository
import com.transigo.app.data.repository.AdminDashboardRepository
import com.transigo.app.data.repository.ProfileRepository
import com.transigo.app.core.service.FcmTokenService
import com.transigo.app.core.service.NotificationService

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.transigo.app.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseMessaging(): FirebaseMessaging = FirebaseMessaging.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository = AuthRepository(firebaseAuth, firestore)
    
    @Provides
    @Singleton
    fun provideDriverRepository(firestore: FirebaseFirestore): DriverRepository {
        return DriverRepository(firestore)
    }
    
    @Provides
    @Singleton
    fun provideNotificationService(firestore: FirebaseFirestore): NotificationService {
        return NotificationService(firestore)
    }
    
    @Provides
    @Singleton
    fun provideAdminBookingRepository(
        firestore: FirebaseFirestore,
        notificationService: NotificationService
    ): AdminBookingRepository {
        return AdminBookingRepository(firestore, notificationService)
    }
    
    @Provides
    @Singleton
    fun provideAdminDashboardRepository(firestore: FirebaseFirestore): AdminDashboardRepository {
        return AdminDashboardRepository(firestore)
    }
    
    @Provides
    @Singleton
    fun provideProfileRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): ProfileRepository {
        return ProfileRepository(firestore, firebaseAuth)
    }
    
    @Provides
    @Singleton
    fun provideFcmTokenService(
        firebaseMessaging: FirebaseMessaging
    ): FcmTokenService {
        return FcmTokenService(firebaseMessaging)
    }
}
