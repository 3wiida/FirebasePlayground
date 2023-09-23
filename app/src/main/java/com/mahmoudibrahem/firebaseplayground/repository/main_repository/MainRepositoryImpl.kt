package com.mahmoudibrahem.firebaseplayground.repository.main_repository

import com.mahmoudibrahem.firebaseplayground.R
import com.mahmoudibrahem.firebaseplayground.pojo.AuthMethod
import com.mahmoudibrahem.firebaseplayground.pojo.FirebaseFeature

class MainRepositoryImpl : MainRepository {
    override fun getFeaturesList(): List<FirebaseFeature> {
        return listOf(
            FirebaseFeature(
                id = 1,
                name="Authentication",
                image = R.drawable.auth_ic
            ),
            FirebaseFeature(
                id = 2,
                name="Remote Config",
                image = R.drawable.remote_config_ic
            ),
            FirebaseFeature(
                id = 3,
                name="Realtime Database",
                image = R.drawable.database_ic
            ),
            FirebaseFeature(
                id = 4,
                name="Firestore Database",
                image = R.drawable.firestore_ic
            ),
            FirebaseFeature(
                id = 5,
                name="Cloud Storage",
                image = R.drawable.cloud_storage_ic
            )
        )
    }

    override fun getAuthMethods(): List<AuthMethod> {
        return listOf(
            AuthMethod(
                id = 1,
                name = "Email & Password",
                image = R.drawable.email_ic
            )
        )
    }
}