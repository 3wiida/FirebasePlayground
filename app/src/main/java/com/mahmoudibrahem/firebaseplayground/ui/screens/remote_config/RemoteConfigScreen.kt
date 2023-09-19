package com.mahmoudibrahem.firebaseplayground.ui.screens.remote_config

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoudibrahem.firebaseplayground.ui.theme.SfDisplay


@Composable
fun RemoteConfigScreen(
    viewModel: RemoteConfigViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        Text(
            text = "Remote Config",
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            modifier = Modifier.padding(top = 16.dp),
        )
        Text(
            text = "Fetch app version form firebase remote config on clicking in the button below",
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp),
        )
        Spacer(modifier = Modifier.height(36.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    viewModel.fetchAppVersion()
                },
                modifier = Modifier.width(150.dp)
            ) {
                Text(text = "Fetch")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "App version is  -> ${viewModel.appVersion.value}",
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = "When button below clicked any update in app version parameter in remote config will be automatically reflect in the text up ⬆",
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    viewModel.listenToAppVersionUpdates()
                },
                modifier = Modifier.width(250.dp)
            ) {
                Text(text = "Listen to app version updates")
            }
        }
    }
}