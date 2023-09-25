package com.mahmoudibrahem.firebaseplayground.repository.realtime_db_repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.util.Constants.NAME_FIELD
import com.mahmoudibrahem.firebaseplayground.util.Constants.SCHOOLS_REF_KEY
import javax.inject.Inject

class RealtimeDatabaseRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase
) : RealtimeDatabaseRepository {

    private val schoolRef = database.getReference(SCHOOLS_REF_KEY)

    override fun observeSchoolsUpdates(onUpdate: (DataSnapshot) -> Unit) {
        database.getReference(SCHOOLS_REF_KEY).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    onUpdate.invoke(snapshot)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
        )
    }

    override fun addSchool(school: School): Task<Void>? {
        val id = schoolRef.push().key
        return if (id != null) {
            val s = School(id = id, name = school.name, address = school.address)
            schoolRef.child(id).setValue(s)
        } else {
            null
        }
    }

    override fun editSchool(schoolId: String, newName: String, newAddress: String): Task<Void> {
        val s = School(id = schoolId, name = newName, address = newAddress)
        return schoolRef.child(schoolId).setValue(s)
    }

    override fun deleteSchool(school: School): Task<Void> {
        return schoolRef.child(school.id).removeValue()
    }

    override fun orderSchoolsASC(onReceiveData: (DataSnapshot) -> Unit) {
        val query = schoolRef.orderByChild(NAME_FIELD)
        query.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    onReceiveData.invoke(snapshot)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
        )
    }

    override fun orderSchoolsDES(onReceiveData: (DataSnapshot) -> Unit) {
        val query = schoolRef.orderByChild(NAME_FIELD)
        query.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    onReceiveData.invoke(snapshot)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
        )
    }

    override fun searchForSchool(query: String, onReceiveData: (DataSnapshot) -> Unit) {
        val databaseQuery = schoolRef.orderByChild(NAME_FIELD).equalTo(query)
        databaseQuery.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    onReceiveData.invoke(snapshot)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
        )
    }
}