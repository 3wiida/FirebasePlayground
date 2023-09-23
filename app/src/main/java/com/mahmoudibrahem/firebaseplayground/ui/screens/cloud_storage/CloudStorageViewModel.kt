package com.mahmoudibrahem.firebaseplayground.ui.screens.cloud_storage

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.lifecycle.ViewModel
import com.mahmoudibrahem.firebaseplayground.repository.cloud_storage.CloudStorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CloudStorageViewModel @Inject constructor(
    private val storageRepository: CloudStorageRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CloudStorageUIState())
    val uiState = _uiState.asStateFlow()

    fun onImageNameChanged(newValue: String) {
        _uiState.update { it.copy(caption = newValue) }
    }

    fun updateUri(uri: Uri?) {
        uri?.let {
            _uiState.update { it.copy(imageUri = uri) }
        }
    }

    fun onUploadButtonClicked(context: Context) {
        _uiState.update { it.copy(isButtonLoading = true) }
        _uiState.value.imageUri?.let { uri ->
            val imageName = getCurrentTimeInMillis().toString()
            val imageExtension = getImageExtension(context, uri)
            if (imageExtension != null) {
                storageRepository.uploadImage(
                    imageName = imageName,
                    imageExtension = imageExtension,
                    uri = uri,
                    caption = _uiState.value.caption
                ).addOnSuccessListener {
                    _uiState.update {
                        it.copy(
                            successMsg = "image uploaded successfully",
                            uploadProgress = 0f,
                            imageUri = null,
                            caption = "",
                            isButtonLoading = false
                        )
                    }
                }.addOnFailureListener { exception ->
                    _uiState.update {
                        it.copy(
                            errorMsg = exception.message.toString(),
                            uploadProgress = 0f,
                            isButtonLoading = false
                        )
                    }
                }.addOnProgressListener { taskSnapShot ->
                    _uiState.update {
                        it.copy(
                            uploadProgress = (taskSnapShot.bytesTransferred / taskSnapShot.totalByteCount.toFloat())
                        )
                    }
                }
            } else {
                _uiState.update { it.copy(errorMsg = "Error in getting image extension") }
            }
        }
    }

    fun onShowImagesClicked(i: Int) {
        getAllImages()
        _uiState.update {
            it.copy(
                showImagesBottomSheet = true,
            )
        }
    }

    private fun getCurrentTimeInMillis(): Long {
        return System.currentTimeMillis()
    }

    private fun getImageExtension(context: Context, uri: Uri): String? {
        val contentResolver = context.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    fun onBottomSheetDismiss() {
        _uiState.update { it.copy(showImagesBottomSheet = false) }
    }

    private fun getAllImages() {
        storageRepository.getImages().addOnSuccessListener { images ->
            images.items.forEach { image ->
                image.downloadUrl.addOnSuccessListener { uri ->
                    _uiState.value.imagesList.add(uri)
                }
            }
        }
    }
}