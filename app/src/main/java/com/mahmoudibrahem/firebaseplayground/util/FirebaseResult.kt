package com.mahmoudibrahem.firebaseplayground.util

sealed class FirebaseResult<out T> {
    object Empty : FirebaseResult<Nothing>()
    object Loading : FirebaseResult<Nothing>()
    data class Failure(val msg: String) : FirebaseResult<Nothing>()
    data class Success<out T>(val data: T) : FirebaseResult<T>()
}
