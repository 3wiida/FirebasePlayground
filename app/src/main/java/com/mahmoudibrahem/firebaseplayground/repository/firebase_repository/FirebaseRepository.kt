package com.mahmoudibrahem.firebaseplayground.repository.firebase_repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.ui.screens.realtime_database.SaveAction
import com.mahmoudibrahem.firebaseplayground.util.FirebaseResult

interface FirebaseRepository {
    val auth: FirebaseAuth
    val remoteConfig: FirebaseRemoteConfig
    val database:FirebaseDatabase

    fun observeSchoolsUpdates(onUpdate:(DataSnapshot)->Unit)
    fun upsertSchool(school: School,action:SaveAction):Task<Void>?
    fun deleteSchool(school: School):Task<Void>
    fun setupRemoteConfig()
    fun fetchAppVersion(): String
    fun listonToAppVersionUpdate(onNewValue: (String) -> Unit)
    fun registerEmailPassword(email: String, password: String): Task<AuthResult>
    fun loginEmailPassword(email: String, password: String): Task<AuthResult>
}