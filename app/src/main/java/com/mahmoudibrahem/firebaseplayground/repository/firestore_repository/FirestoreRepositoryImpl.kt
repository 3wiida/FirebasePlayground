package com.mahmoudibrahem.firebaseplayground.repository.firestore_repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.util.Constants.ADDRESS_FIELD
import com.mahmoudibrahem.firebaseplayground.util.Constants.NAME_FIELD
import com.mahmoudibrahem.firebaseplayground.util.Constants.SCHOOLS_COLLECTION
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : FirestoreRepository {
    override fun observeSchools(
        onUpdate: (QuerySnapshot?) -> Unit,
        onError: (FirebaseFirestoreException?) -> Unit
    ) {
        db.collection(SCHOOLS_COLLECTION).addSnapshotListener { value, error ->
            if (error != null) {
                onError.invoke(error)
                return@addSnapshotListener
            }
            onUpdate(value)
        }
    }

    override fun searchForSchool(query: String): Task<QuerySnapshot> {
        return db.collection(SCHOOLS_COLLECTION)
            .whereEqualTo(NAME_FIELD, query)
            .get()
    }

    override fun orderSchoolsASC(): Task<QuerySnapshot> {
        return db.collection(SCHOOLS_COLLECTION)
            .orderBy(NAME_FIELD, Query.Direction.ASCENDING)
            .get()
    }

    override fun orderSchoolsDES(): Task<QuerySnapshot> {
        return db.collection(SCHOOLS_COLLECTION)
            .orderBy(NAME_FIELD, Query.Direction.DESCENDING)
            .get()
    }

    override fun deleteSchool(school: School): Task<Void> {
        return db.collection(SCHOOLS_COLLECTION).document(school.id).delete()
    }

    override fun addSchool(school: School): Task<Void> {
        val id = db.collection(SCHOOLS_COLLECTION).document().id
        val newSchool = School(id = id, name = school.name, address = school.address)
        return db.collection(SCHOOLS_COLLECTION).document(id).set(newSchool)
    }

    override fun editSchool(schoolId: String, newName: String, newAddress: String): Task<Void> {
        return db.collection(SCHOOLS_COLLECTION).document(schoolId).update(
            NAME_FIELD, newName,
            ADDRESS_FIELD, newAddress
        )
    }
}