package com.mahmoudibrahem.firebaseplayground.ui.screens.auth.email_password

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mahmoudibrahem.firebaseplayground.repository.firebase_repository.FirebaseRepository
import com.mahmoudibrahem.firebaseplayground.util.FirebaseResult
import com.mahmoudibrahem.firebaseplayground.util.PossibleFormErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EmailPasswordRegisterViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    val registerState = mutableStateOf<FirebaseResult<*>>(FirebaseResult.Empty)
    val loginState = mutableStateOf<FirebaseResult<*>>(FirebaseResult.Empty)


    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val formErrors = mutableStateListOf<PossibleFormErrors>()

    fun registerUser() {
        isFormValid()
        if (formErrors.isEmpty()) {
            registerState.value = FirebaseResult.Loading
            firebaseRepository.registerEmailPassword(
                email = email.value,
                password = password.value
            ).addOnSuccessListener {
                it.user?.uid?.let { uid ->
                    registerState.value = FirebaseResult.Success(data = uid)
                }
            }.addOnFailureListener { exception ->
                exception.message?.let { msg ->
                    registerState.value = FirebaseResult.Failure(msg = msg)
                }
            }
        }
    }

    fun loginUser() {
        isFormValid()
        if (formErrors.isEmpty()) {
            loginState.value = FirebaseResult.Loading
            firebaseRepository.loginEmailPassword(
                email = email.value,
                password = password.value
            ).addOnSuccessListener {
                it.user?.uid?.let { uid -> loginState.value = FirebaseResult.Success(data = uid) }
            }.addOnFailureListener { exception ->
                exception.message?.let { msg ->
                    loginState.value = FirebaseResult.Failure(msg = msg)
                }
            }
        }
    }

    private fun isFormValid() {
        formErrors.clear()
        if (!email.value.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")) || email.value.isEmpty())
            formErrors.add(PossibleFormErrors.EMAIL_INVALID)
        if (password.value.length < 8)
            formErrors.add(PossibleFormErrors.PASSWORD_INVALID)
    }

}

