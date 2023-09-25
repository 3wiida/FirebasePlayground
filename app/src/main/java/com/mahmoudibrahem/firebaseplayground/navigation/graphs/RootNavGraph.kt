package com.mahmoudibrahem.firebaseplayground.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mahmoudibrahem.firebaseplayground.navigation.screens.Destinations
import com.mahmoudibrahem.firebaseplayground.ui.screens.cloud_messaging.CloudMessagingScreen
import com.mahmoudibrahem.firebaseplayground.ui.screens.cloud_storage.CloudStorageScreen
import com.mahmoudibrahem.firebaseplayground.ui.screens.firestore.FirestoreScreen
import com.mahmoudibrahem.firebaseplayground.ui.screens.realtime_database.RealtimeDatabaseScreen
import com.mahmoudibrahem.firebaseplayground.ui.screens.remote_config.RemoteConfigScreen
import com.mahmoudibrahem.firebaseplayground.ui.screens.root.RootScreen
import com.mahmoudibrahem.firebaseplayground.util.Constants.AUTH_GRAPH_ROUTE
import com.mahmoudibrahem.firebaseplayground.util.Constants.ROOT_GRAPH_ROUTE
import com.mahmoudibrahem.firebaseplayground.util.Constants.ROOT_SCREEN_ROUTE

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ROOT_SCREEN_ROUTE,
        route = ROOT_GRAPH_ROUTE
    ) {
        composable(route = ROOT_SCREEN_ROUTE) {
            RootScreen(
                onNavigateToAuth = { navController.navigate(route = AUTH_GRAPH_ROUTE) },
                onNavigateToRemoteConfig = { navController.navigate(route = Destinations.RemoteConfigScreen.route) },
                onNavigateToRealtimeDatabase = { navController.navigate(route = Destinations.RealtimeDatabaseScreen.route) },
                onNavigateToFirestore = { navController.navigate(route = Destinations.FirestoreDatabaseScreen.route) },
                onNavigateToCloudStorage = { navController.navigate(route = Destinations.StorageScreen.route) },
                onNavigateToCloudMessaging = { navController.navigate(route = Destinations.CloudMessaging.route) }
            )
        }
        composable(route = Destinations.RemoteConfigScreen.route) {
            RemoteConfigScreen()
        }
        composable(route = Destinations.RealtimeDatabaseScreen.route) {
            RealtimeDatabaseScreen()
        }
        composable(route = Destinations.FirestoreDatabaseScreen.route) {
            FirestoreScreen()
        }
        composable(route = Destinations.StorageScreen.route) {
            CloudStorageScreen()
        }
        composable(route=Destinations.CloudMessaging.route){
            CloudMessagingScreen()
        }
        authNavGraph(navController = navController)
    }
}