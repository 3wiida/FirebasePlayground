package com.mahmoudibrahem.firebaseplayground.ui.screens.auth

import com.mahmoudibrahem.firebaseplayground.pojo.AuthMethod
import javax.annotation.concurrent.Immutable

@Immutable
data class AuthScreenUIState(
    val methods: List<AuthMethod> = emptyList()
)
