package com.mahmoudibrahem.firebaseplayground.ui.screens.remote_config

data class RemoteConfigUIState(
    val appVersion: String = "",
    val errorMessage: String = "",
    val isFetchButtonLoading: Boolean = false,
    val isListenButtonEnabled: Boolean = true
)