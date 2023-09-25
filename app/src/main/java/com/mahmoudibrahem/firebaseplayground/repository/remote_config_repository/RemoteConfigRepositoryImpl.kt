package com.mahmoudibrahem.firebaseplayground.repository.remote_config_repository

import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.mahmoudibrahem.firebaseplayground.R
import com.mahmoudibrahem.firebaseplayground.util.Constants.APP_VERSION_KEY
import javax.inject.Inject

class RemoteConfigRepositoryImpl @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : RemoteConfigRepository {
    override fun setupRemoteConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3
        }
        remoteConfig.run {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(R.xml.remote_config_defaults)
        }
    }

    override fun listonToAppVersionUpdate(onNewValue: (Task<Boolean>) -> Unit) {
        remoteConfig.addOnConfigUpdateListener(
            object : ConfigUpdateListener {
                override fun onUpdate(configUpdate: ConfigUpdate) {
                    if (configUpdate.updatedKeys.contains(APP_VERSION_KEY)) {
                        onNewValue.invoke(fetchAppVersion())
                    }
                }

                override fun onError(error: FirebaseRemoteConfigException) {}

            }
        )
    }

    override fun fetchAppVersion(): Task<Boolean> {
        return remoteConfig.fetchAndActivate()
    }

    override fun getVersion(): String {
        return remoteConfig.getString(APP_VERSION_KEY)
    }
}