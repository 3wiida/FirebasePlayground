package com.mahmoudibrahem.firebaseplayground.repository.realtime_db_repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.ui.screens.realtime_database.SaveAction

interface RealtimeDatabaseRepository {
    fun observeSchoolsUpdates(onUpdate: (DataSnapshot) -> Unit)
    fun deleteSchool(school: School): Task<Void>
    fun orderSchoolsASC(onReceiveData: (DataSnapshot) -> Unit)
    fun orderSchoolsDES(onReceiveData: (DataSnapshot) -> Unit)
    fun searchForSchool(query: String, onReceiveData: (DataSnapshot) -> Unit)
    fun addSchool(school: School):Task<Void>?
    fun editSchool(schoolId: String,newName:String,newAddress:String):Task<Void>
}