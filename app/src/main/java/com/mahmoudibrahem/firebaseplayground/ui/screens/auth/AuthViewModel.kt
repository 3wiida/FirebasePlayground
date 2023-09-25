package com.mahmoudibrahem.firebaseplayground.ui.screens.auth

import androidx.lifecycle.ViewModel
import com.mahmoudibrahem.firebaseplayground.repository.main_repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthScreenUIState())
    val uiState = _uiState.asStateFlow()

    init {
        getAuthMethods()
    }
    private fun getAuthMethods() {
        _uiState.update { it.copy(methods = mainRepository.getAuthMethods()) }
    }

}