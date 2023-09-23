package com.mahmoudibrahem.firebaseplayground.repository.cloud_storage

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.UploadTask

interface CloudStorageRepository {
    fun uploadImage(imageName: String, imageExtension: String,uri: Uri,caption:String=""): UploadTask

    fun getImages(): Task<ListResult>
}