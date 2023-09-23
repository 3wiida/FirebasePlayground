package com.mahmoudibrahem.firebaseplayground.repository.cloud_storage

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storageMetadata
import com.mahmoudibrahem.firebaseplayground.util.Constants.IMAGES_REF


class CloudStorageRepositoryImpl(
    private val storage: FirebaseStorage
) : CloudStorageRepository {
    private val imageRef = storage.getReference(IMAGES_REF)
    override fun uploadImage(
        imageName: String,
        imageExtension: String,
        uri: Uri,
        caption: String
    ): UploadTask {
        return imageRef
            .child("$imageName.$imageExtension")
            .putFile(uri, storageMetadata { setCustomMetadata("caption", caption) })
    }

    override fun getImages(): Task<ListResult> {
        return imageRef.listAll()
    }
}