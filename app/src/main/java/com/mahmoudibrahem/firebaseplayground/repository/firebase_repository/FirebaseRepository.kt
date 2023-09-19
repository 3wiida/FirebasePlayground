package com.mahmoudibrahem.firebaseplayground.repository.firebase_repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.mahmoudibrahem.firebaseplayground.util.FirebaseResult

interface FirebaseRepository {
    val auth: FirebaseAuth
    val remoteConfig: FirebaseRemoteConfig
    fun setupRemoteConfig()
    fun fetchAppVersion(): String
    fun listonToAppVersionUpdate(onNewValue: (String) -> Unit)
    fun registerEmailPassword(email: String, password: String): Task<AuthResult>
    fun loginEmailPassword(email: String, password: String): Task<AuthResult>
}