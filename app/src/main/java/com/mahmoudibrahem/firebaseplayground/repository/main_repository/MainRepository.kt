package com.mahmoudibrahem.firebaseplayground.repository.main_repository

import com.mahmoudibrahem.firebaseplayground.pojo.AuthMethod
import com.mahmoudibrahem.firebaseplayground.pojo.FirebaseFeature

interface MainRepository {
    fun getFeaturesList(): List<FirebaseFeature>
    fun getAuthMethods(): List<AuthMethod>
}