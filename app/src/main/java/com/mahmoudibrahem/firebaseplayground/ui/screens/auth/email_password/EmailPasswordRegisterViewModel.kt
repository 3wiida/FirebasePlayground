package com.mahmoudibrahem.firebaseplayground.ui.screens.auth.email_password

import androidx.lifecycle.ViewModel
import com.mahmoudibrahem.firebaseplayground.repository.auth_repository.AuthRepository
import com.mahmoudibrahem.firebaseplayground.util.PossibleFormErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EmailPasswordRegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmailPasswordUIState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
    }

    fun onPasswordChanged(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
    }

    fun onRegisterClicked() {
        isFormValid()
        if (_uiState.value.errors.isEmpty()) {
            _uiState.update { it.copy(errorMsg = "", successMsg = "", isRegisterLoading = true) }
            authRepository.registerEmailPassword(
                email = _uiState.value.email,
                password = _uiState.value.password
            ).addOnSuccessListener { result ->
                _uiState.update {
                    it.copy(
                        successMsg = "Success UID = ${result.user?.uid}",
                        isRegisterLoading = false
                    )
                }
            }.addOnFailureListener { exception ->
                _uiState.update {
                    it.copy(
                        errorMsg = exception.message.toString(),
                        isRegisterLoading = false
                    )
                }
            }
        }
    }

    fun onLoginClicked() {
        isFormValid()
        if (_uiState.value.errors.isEmpty()) {
            _uiState.update { it.copy(errorMsg = "", successMsg = "", isLoginLoading = true) }
            authRepository.loginEmailPassword(
                email = _uiState.value.email,
                password = _uiState.value.password
            ).addOnSuccessListener { result ->
                _uiState.update {
                    it.copy(
                        successMsg = "Success UID = ${result.user?.uid}",
                        isLoginLoading = false
                    )
                }
            }.addOnFailureListener { exception ->
                _uiState.update {
                    it.copy(
                        errorMsg = exception.message.toString(),
                        isLoginLoading = false
                    )
                }
            }
        }
    }

    private fun isFormValid() {
        _uiState.value.errors.clear()
        val email = _uiState.value.email
        val password = _uiState.value.password
        if (!email.matches(Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")) || email.isEmpty())
            _uiState.value.errors.add(PossibleFormErrors.EMAIL_INVALID)
        if (password.length < 8)
            _uiState.value.errors.add(PossibleFormErrors.PASSWORD_INVALID)
    }

}

