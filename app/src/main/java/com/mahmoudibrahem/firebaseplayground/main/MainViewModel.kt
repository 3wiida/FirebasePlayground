package com.mahmoudibrahem.firebaseplayground.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _isKeepSplashScreen = MutableStateFlow(true)
    val isKeepSplashScreen = _isKeepSplashScreen.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1500)
            _isKeepSplashScreen.value = false
        }
    }
}