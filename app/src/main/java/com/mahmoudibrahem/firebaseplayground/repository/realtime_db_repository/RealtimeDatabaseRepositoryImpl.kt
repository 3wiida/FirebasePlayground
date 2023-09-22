package com.mahmoudibrahem.firebaseplayground.repository.realtime_db_repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.ui.screens.realtime_database.SaveAction
import com.mahmoudibrahem.firebaseplayground.util.Constants
import javax.inject.Inject

class RealtimeDatabaseRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase
) : RealtimeDatabaseRepository {
    override fun observeSchoolsUpdates(onUpdate: (DataSnapshot) -> Unit) {
        database.getReference(Constants.SCHOOLS_REF_KEY).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    onUpdate.invoke(snapshot)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
        )
    }

    override fun upsertSchool(school: School, action: SaveAction): Task<Void>? {
        val id = if (action == SaveAction.ADD_NEW)
            database.getReference(Constants.SCHOOLS_REF_KEY).push().key
        else school.id

        return if (id != null) {
            val s = School(id = id, name = school.name, address = school.address)
            database.getReference(Constants.SCHOOLS_REF_KEY).child(id).setValue(s)
        } else {
            null
        }
    }

    override fun deleteSchool(school: School): Task<Void> {
        return database.getReference(Constants.SCHOOLS_REF_KEY).child(school.id).removeValue()
    }
}