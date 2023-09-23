package com.mahmoudibrahem.firebaseplayground.navigation.screens

import com.mahmoudibrahem.firebaseplayground.util.Constants.AUTH_SCREENS_ROUTE
import com.mahmoudibrahem.firebaseplayground.util.Constants.CLOUD_STORAGE_SCREEN_ROUTE
import com.mahmoudibrahem.firebaseplayground.util.Constants.EMAIL_PASSWORD_REGISTRATION_SCREEN_ROUTE
import com.mahmoudibrahem.firebaseplayground.util.Constants.FIRESTORE_DATABASE_SCREEN_ROUTE
import com.mahmoudibrahem.firebaseplayground.util.Constants.REALTIME_DATABASE_SCREEN_ROUTE
import com.mahmoudibrahem.firebaseplayground.util.Constants.REMOTE_CONFIG_SCREEN_ROUTE


sealed class Destinations(val route: String) {
    object AuthScreens : Destinations(route = AUTH_SCREENS_ROUTE)
    object EmailPasswordRegistration : Destinations(route = EMAIL_PASSWORD_REGISTRATION_SCREEN_ROUTE)
    object RemoteConfigScreen : Destinations(route = REMOTE_CONFIG_SCREEN_ROUTE)
    object RealtimeDatabaseScreen:Destinations(route = REALTIME_DATABASE_SCREEN_ROUTE)
    object FirestoreDatabaseScreen:Destinations(route = FIRESTORE_DATABASE_SCREEN_ROUTE)
    object StorageScreen:Destinations(route = CLOUD_STORAGE_SCREEN_ROUTE)
}


