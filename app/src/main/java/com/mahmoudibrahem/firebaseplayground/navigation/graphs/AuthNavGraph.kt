package com.mahmoudibrahem.firebaseplayground.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mahmoudibrahem.firebaseplayground.navigation.screens.Destinations
import com.mahmoudibrahem.firebaseplayground.ui.screens.auth.AuthScreen
import com.mahmoudibrahem.firebaseplayground.ui.screens.auth.email_password.EmailPasswordScreen
import com.mahmoudibrahem.firebaseplayground.util.Constants.AUTH_GRAPH_ROUTE

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        startDestination = Destinations.AuthScreens.route,
        route = AUTH_GRAPH_ROUTE
    ) {
        composable(route = Destinations.AuthScreens.route) {
            AuthScreen(
                onNavigateToEmailPassword = { navController.navigate(route = Destinations.EmailPasswordRegistration.route) }
            )
        }
        composable(route = Destinations.EmailPasswordRegistration.route) {
            EmailPasswordScreen()
        }
    }
}
