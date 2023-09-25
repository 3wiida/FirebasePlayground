package com.mahmoudibrahem.firebaseplayground.ui.screens.remote_config

import androidx.lifecycle.ViewModel
import com.mahmoudibrahem.firebaseplayground.repository.remote_config_repository.RemoteConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RemoteConfigViewModel @Inject constructor(
    private val remoteConfigRepository: RemoteConfigRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RemoteConfigUIState())
    val uiState = _uiState.asStateFlow()


    init {
        remoteConfigRepository.setupRemoteConfig()
    }

    fun onFetchClicked() {
        _uiState.update { it.copy(errorMessage = "", isFetchButtonLoading = true) }
        remoteConfigRepository.fetchAppVersion()
            .addOnSuccessListener {
                _uiState.update {
                    it.copy(
                        appVersion = remoteConfigRepository.getVersion(),
                        isFetchButtonLoading = false
                    )
                }
            }
            .addOnFailureListener { exception ->
                _uiState.update {
                    it.copy(
                        errorMessage = exception.message.toString(),
                        isFetchButtonLoading = false
                    )
                }
            }
    }

    fun onListenToAppVersionClicked() {
        _uiState.update { it.copy(isListenButtonEnabled = false, errorMessage = "") }
        remoteConfigRepository.listonToAppVersionUpdate { task ->
            task.addOnSuccessListener {
                _uiState.update { it.copy(appVersion = remoteConfigRepository.getVersion()) }
            }.addOnFailureListener { exception ->
                _uiState.update { it.copy(errorMessage = exception.message.toString()) }
            }
        }
    }

}