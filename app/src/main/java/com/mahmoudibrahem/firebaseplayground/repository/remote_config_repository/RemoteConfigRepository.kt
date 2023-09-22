package com.mahmoudibrahem.firebaseplayground.repository.remote_config_repository

interface RemoteConfigRepository {
    fun setupRemoteConfig()
    fun fetchAppVersion(): String
    fun listonToAppVersionUpdate(onNewValue: (String) -> Unit)
}