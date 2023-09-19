package com.mahmoudibrahem.firebaseplayground.ui.screens.remote_config

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mahmoudibrahem.firebaseplayground.repository.firebase_repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RemoteConfigViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    val appVersion = mutableStateOf("")

    init {
        firebaseRepository.setupRemoteConfig()
    }

    fun fetchAppVersion() {
        appVersion.value = firebaseRepository.fetchAppVersion()
    }

    fun listenToAppVersionUpdates() {
        firebaseRepository.listonToAppVersionUpdate { newValue ->
            appVersion.value = newValue
        }
    }
}