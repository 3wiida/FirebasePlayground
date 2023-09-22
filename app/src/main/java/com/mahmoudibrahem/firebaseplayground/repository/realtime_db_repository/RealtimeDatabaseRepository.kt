package com.mahmoudibrahem.firebaseplayground.repository.realtime_db_repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.ui.screens.realtime_database.SaveAction

interface RealtimeDatabaseRepository {
    fun observeSchoolsUpdates(onUpdate:(DataSnapshot)->Unit)
    fun upsertSchool(school: School, action: SaveAction): Task<Void>?
    fun deleteSchool(school: School): Task<Void>
}