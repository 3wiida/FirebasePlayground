package com.mahmoudibrahem.firebaseplayground.repository.firebase_repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.mahmoudibrahem.firebaseplayground.R
import com.mahmoudibrahem.firebaseplayground.util.Constants.APP_VERSION_KEY
import com.mahmoudibrahem.firebaseplayground.util.FirebaseResult

class FirebaseRepositoryImpl : FirebaseRepository {
    override val auth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    override val remoteConfig: FirebaseRemoteConfig
        get() = Firebase.remoteConfig

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