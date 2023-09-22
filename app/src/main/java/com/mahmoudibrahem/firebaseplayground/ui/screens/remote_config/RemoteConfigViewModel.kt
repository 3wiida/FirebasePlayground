package com.mahmoudibrahem.firebaseplayground.ui.screens.remote_config

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mahmoudibrahem.firebaseplayground.repository.remote_config_repository.RemoteConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RemoteConfigViewModel @Inject constructor(
    private val remoteConfigRepository: RemoteConfigRepository
) : ViewModel() {

    val appVersion = mutableStateOf("")

    init {
        remoteConfigRepository.setupRemoteConfig()
    }

    fun fetchAppVersion() {
        appVersion.value = remoteConfigRepository.fetchAppVersion()
    }

    fun listenToAppVersionUpdates() {
        remoteConfigRepository.listonToAppVersionUpdate { newValue ->
            appVersion.value = newValue
        }
    }
}