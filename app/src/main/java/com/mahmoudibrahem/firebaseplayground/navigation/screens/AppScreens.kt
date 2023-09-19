package com.mahmoudibrahem.firebaseplayground.navigation.screens

import com.mahmoudibrahem.firebaseplayground.util.Constants.AUTH_SCREENS_ROUTE
import com.mahmoudibrahem.firebaseplayground.util.Constants.EMAIL_PASSWORD_REGISTRATION_SCREEN_ROUTE
import com.mahmoudibrahem.firebaseplayground.util.Constants.REMOTE_CONFIG_SCREEN_ROUTE


sealed class Destinations(val route: String) {
    object AuthScreens : Destinations(route = AUTH_SCREENS_ROUTE)
    object EmailPasswordRegistration : Destinations(route = EMAIL_PASSWORD_REGISTRATION_SCREEN_ROUTE)
    object RemoteConfigScreen : Destinations(route = REMOTE_CONFIG_SCREEN_ROUTE)
}


