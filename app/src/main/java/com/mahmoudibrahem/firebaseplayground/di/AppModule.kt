package com.mahmoudibrahem.firebaseplayground.di

import com.mahmoudibrahem.firebaseplayground.repository.firebase_repository.FirebaseRepository
import com.mahmoudibrahem.firebaseplayground.repository.firebase_repository.FirebaseRepositoryImpl
import com.mahmoudibrahem.firebaseplayground.repository.main_repository.MainRepository
import com.mahmoudibrahem.firebaseplayground.repository.main_repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Provides
    @Singleton
    fun provideMainRepo(): MainRepository = MainRepositoryImpl()

    @Provides
    @Singleton
    fun provideFirebaseRepo(): FirebaseRepository = FirebaseRepositoryImpl()
}