package com.mahmoudibrahem.firebaseplayground.util

object Constants {
    //Screens routes
    const val REMOTE_CONFIG_SCREEN_ROUTE = "REMOTE_CONFIG_SCREEN_ROUTE"
    const val EMAIL_PASSWORD_REGISTRATION_SCREEN_ROUTE = "EMAIL_PASSWORD_REGISTRATION_SCREEN_ROUTE"
    const val REALTIME_DATABASE_SCREEN_ROUTE = "REALTIME_DATABASE_SCREEN_ROUTE"
    const val FIRESTORE_DATABASE_SCREEN_ROUTE = "FIRESTORE_DATABASE_SCREEN_ROUTE"
    const val CLOUD_STORAGE_SCREEN_ROUTE = "CLOUD_STORAGE_SCREEN_ROUTE"
    const val CLOUD_MESSAGING_SCREEN_ROUTE = "CLOUD_MESSAGING_SCREEN_ROUTE"
    const val ROOT_SCREEN_ROUTE = "ROOT_SCREEN_ROUTE"
    const val AUTH_SCREENS_ROUTE = "AUTH_SCREENS_ROUTE"

    //Graphs routes
    const val ROOT_GRAPH_ROUTE = "ROOT_GRAPH_ROUTE"
    const val AUTH_GRAPH_ROUTE = "AUTH_GRAPH_ROUTE"
    const val REGISTER_GRAPH_ROUTE = "REGISTER_GRAPH_ROUTE"

    //Remote Config Keys
    const val APP_VERSION_KEY = "app_version"

    //Realtime Database
    const val SCHOOLS_REF_KEY = "Schools"

    //Firestore Database
    const val SCHOOLS_COLLECTION = "schools"
    const val NAME_FIELD = "name"
    const val ADDRESS_FIELD = "address"

    //Cloud Storage
    const val IMAGES_REF = "images"



    //Notification
    const val NOTIFICATION_CHANNEL_ID = "FIREBASE_PLAYGROUND_NOTIFICATION_CHANNEL_ID"
    const val NOTIFICATION_CHANNEL_NAME = "Firebase Playground Notification Channel"
    const val NOTIFICATION_CHANNEL_DESCRIPTION = "This is the notification channel for firebase playground"
}
