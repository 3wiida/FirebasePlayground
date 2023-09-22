package com.mahmoudibrahem.firebaseplayground.repository.auth_repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthRepository {
    fun registerEmailPassword(email: String, password: String): Task<AuthResult>
    fun loginEmailPassword(email: String, password: String): Task<AuthResult>
}