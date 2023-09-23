package com.mahmoudibrahem.firebaseplayground.ui.screens.cloud_storage

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class CloudStorageUIState(
    val caption: String = "",
    val imageUri: Uri? = null,
    val uploadProgress: Float = 0.0f,
    val isButtonLoading: Boolean = false,
    val errorMsg: String = "",
    val successMsg: String = "",
    val imagesList: SnapshotStateList<Uri?> = mutableStateListOf(),
    val showImagesBottomSheet: Boolean = false
)
