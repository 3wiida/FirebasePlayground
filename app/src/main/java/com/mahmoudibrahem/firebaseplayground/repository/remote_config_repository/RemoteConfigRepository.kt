package com.mahmoudibrahem.firebaseplayground.repository.remote_config_repository

import com.google.android.gms.tasks.Task

interface RemoteConfigRepository {
    fun setupRemoteConfig()
    fun fetchAppVersion(): Task<Boolean>
    fun getVersion():String
    fun listonToAppVersionUpdate(onNewValue: (Task<Boolean>) -> Unit)
}