package com.mahmoudibrahem.firebaseplayground.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.storage.FirebaseStorage
import com.mahmoudibrahem.firebaseplayground.repository.auth_repository.AuthRepository
import com.mahmoudibrahem.firebaseplayground.repository.auth_repository.AuthRepositoryImpl
import com.mahmoudibrahem.firebaseplayground.repository.cloud_storage.CloudStorageRepository
import com.mahmoudibrahem.firebaseplayground.repository.cloud_storage.CloudStorageRepositoryImpl
import com.mahmoudibrahem.firebaseplayground.repository.firestore_repository.FirestoreRepository
import com.mahmoudibrahem.firebaseplayground.repository.firestore_repository.FirestoreRepositoryImpl
import com.mahmoudibrahem.firebaseplayground.repository.main_repository.MainRepository
import com.mahmoudibrahem.firebaseplayground.repository.main_repository.MainRepositoryImpl
import com.mahmoudibrahem.firebaseplayground.repository.realtime_db_repository.RealtimeDatabaseRepository
import com.mahmoudibrahem.firebaseplayground.repository.realtime_db_repository.RealtimeDatabaseRepositoryImpl
import com.mahmoudibrahem.firebaseplayground.repository.remote_config_repository.RemoteConfigRepository
import com.mahmoudibrahem.firebaseplayground.repository.remote_config_repository.RemoteConfigRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMainRepo(): MainRepository = MainRepositoryImpl()

    @Provides
    @Singleton
    fun provideAuthRepo(auth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(auth)
    }

    @Provides
    @Singleton
    fun provideRemoteConfigRepo(remoteConfig: FirebaseRemoteConfig): RemoteConfigRepository {
        return RemoteConfigRepositoryImpl(remoteConfig)
    }

    @Provides
    @Singleton
    fun provideRealtimeDatabaseRepo(db: FirebaseDatabase): RealtimeDatabaseRepository {
        return RealtimeDatabaseRepositoryImpl(db)
    }

    @Provides
    @Singleton
    fun provideFirestoreRepo(db: FirebaseFirestore): FirestoreRepository {
        return FirestoreRepositoryImpl(db)
    }

    @Provides
    @Singleton
    fun provideCloudStorageRepo(storage:FirebaseStorage):CloudStorageRepository{
        return CloudStorageRepositoryImpl(storage)
    }
}