package com.mahmoudibrahem.firebaseplayground.repository.firestore_repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.mahmoudibrahem.firebaseplayground.pojo.School

interface FirestoreRepository {
    fun observeSchools(
        onUpdate: (QuerySnapshot?) -> Unit,
        onError: (FirebaseFirestoreException?) -> Unit
    )

    fun searchForSchool(query: String): Task<QuerySnapshot>

    fun orderSchoolsASC(): Task<QuerySnapshot>

    fun orderSchoolsDES(): Task<QuerySnapshot>

    fun deleteSchool(school: School): Task<Void>

    fun addSchool(school: School): Task<Void>

    fun editSchool(schoolId: String, newName: String, newAddress: String): Task<Void>

}