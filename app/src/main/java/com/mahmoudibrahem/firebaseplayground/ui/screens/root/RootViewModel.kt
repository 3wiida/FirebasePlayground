package com.mahmoudibrahem.firebaseplayground.ui.screens.root

import androidx.lifecycle.ViewModel
import com.mahmoudibrahem.firebaseplayground.repository.main_repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    fun getFeaturesList() = mainRepository.getFeaturesList()
}