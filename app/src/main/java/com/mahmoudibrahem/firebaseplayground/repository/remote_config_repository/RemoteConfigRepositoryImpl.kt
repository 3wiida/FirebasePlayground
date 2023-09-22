package com.mahmoudibrahem.firebaseplayground.repository.remote_config_repository

import android.util.Log
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.mahmoudibrahem.firebaseplayground.R
import com.mahmoudibrahem.firebaseplayground.util.Constants
import javax.inject.Inject

class RemoteConfigRepositoryImpl @Inject constructor(
    private val remoteConfig:FirebaseRemoteConfig
):RemoteConfigRepository {
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
                if (configUpdate.updatedKeys.contains(Constants.APP_VERSION_KEY)) {
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
        return remoteConfig.getString(Constants.APP_VERSION_KEY)
    }
}