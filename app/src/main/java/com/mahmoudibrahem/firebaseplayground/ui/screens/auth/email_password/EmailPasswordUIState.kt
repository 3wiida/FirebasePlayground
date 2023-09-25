package com.mahmoudibrahem.firebaseplayground.ui.screens.auth.email_password

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mahmoudibrahem.firebaseplayground.util.PossibleFormErrors
import javax.annotation.concurrent.Immutable

@Immutable
data class EmailPasswordUIState(
    val email: String = "",
    val password: String = "",
    val isLoginLoading: Boolean = false,
    val isRegisterLoading: Boolean = false,
    val errors: SnapshotStateList<PossibleFormErrors> = mutableStateListOf(),
    val successMsg: String = "",
    val errorMsg: String = ""
)
