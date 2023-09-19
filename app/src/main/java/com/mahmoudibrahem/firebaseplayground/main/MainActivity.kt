package com.mahmoudibrahem.firebaseplayground.main

import  android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.mahmoudibrahem.firebaseplayground.navigation.graphs.RootNavGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        lifecycleScope.launch {
            viewModel.isKeepSplashScreen.collectLatest {
                splashScreen.setKeepOnScreenCondition { it }
            }
        }
        setContent {
            val systemUiController= rememberSystemUiController()
            val navController= rememberNavController()
            systemUiController.setStatusBarColor(Color.White)
            RootNavGraph(navController = navController)
        }
    }
}

