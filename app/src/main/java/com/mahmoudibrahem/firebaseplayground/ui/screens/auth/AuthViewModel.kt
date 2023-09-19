package com.mahmoudibrahem.firebaseplayground.ui.screens.auth

import androidx.lifecycle.ViewModel
import com.mahmoudibrahem.firebaseplayground.repository.firebase_repository.FirebaseRepository
import com.mahmoudibrahem.firebaseplayground.repository.main_repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val firebaseRepository: FirebaseRepository,
) : ViewModel() {

    fun getAuthMethods() = mainRepository.getAuthMethods()

}