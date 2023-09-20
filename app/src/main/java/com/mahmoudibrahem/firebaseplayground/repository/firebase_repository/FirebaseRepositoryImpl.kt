package com.mahmoudibrahem.firebaseplayground.repository.firebase_repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.mahmoudibrahem.firebaseplayground.R
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.ui.screens.realtime_database.SaveAction
import com.mahmoudibrahem.firebaseplayground.util.Constants.APP_VERSION_KEY
import com.mahmoudibrahem.firebaseplayground.util.Constants.SCHOOLS_REF_KEY
import com.mahmoudibrahem.firebaseplayground.util.Constants.STUDENTS_REF_KEY
import com.mahmoudibrahem.firebaseplayground.util.FirebaseResult

class FirebaseRepositoryImpl : FirebaseRepository {
    override val auth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    override val remoteConfig: FirebaseRemoteConfig
        get() = Firebase.remoteConfig

    override val database: FirebaseDatabase
        get() = Firebase.database

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

    override fun upsertSchool(school: School, action: SaveAction): Task<Void>? {
        val id = if (action == SaveAction.ADD_NEW)
            database.getReference(SCHOOLS_REF_KEY).push().key
         else school.id

        return if (id != null) {
            val s = School(id = id, name = school.name, address = school.address)
            database.getReference(SCHOOLS_REF_KEY).child(id).setValue(s)
        } else {
            null
        }
    }

    override fun deleteSchool(school: School):Task<Void> {
        return database.getReference(SCHOOLS_REF_KEY).child(school.id).removeValue()
    }

    override fun setupRemoteConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3000
        }
        remoteConfig.run {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(R.xml.remote_config_defaults)
        }
    }

    override fun listonToAppVersionUpdate(onNewValue: (String) -> Unit) {
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                if (configUpdate.updatedKeys.contains(APP_VERSION_KEY)) {
                    remoteConfig.activate()
                    onNewValue.invoke(fetchAppVersion())
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.d("``TAG``", "onError: ${error.message}")
            }

        })
    }

    override fun fetchAppVersion(): String {
        return remoteConfig.getString(APP_VERSION_KEY)
    }

    override fun registerEmailPassword(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override fun loginEmailPassword(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }
}