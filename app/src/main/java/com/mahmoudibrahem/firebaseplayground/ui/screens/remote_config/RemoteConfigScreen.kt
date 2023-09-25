package com.mahmoudibrahem.firebaseplayground.ui.screens.remote_config

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoudibrahem.firebaseplayground.R
import com.mahmoudibrahem.firebaseplayground.ui.theme.SfDisplay

@Composable
fun RemoteConfigScreen(
    viewModel: RemoteConfigViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val uiState by viewModel.uiState.collectAsState()
    RemoteConfigContent(
        uiState = uiState,
        onFetchClicked = viewModel::onFetchClicked,
        onListenToAppVersionClicked = viewModel::onListenToAppVersionClicked
    )
    LaunchedEffect(key1 = uiState.errorMessage) {
        if (uiState.errorMessage.isNotEmpty())
            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_LONG).show()
    }
}

@Composable
fun RemoteConfigContent(
    uiState: RemoteConfigUIState,
    onFetchClicked: () -> Unit,
    onListenToAppVersionClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        RemoteConfigHeader()
        Spacer(modifier = Modifier.height(36.dp))
        FetchSection(
            appVersion = uiState.appVersion,
            isFetchButtonLoading = uiState.isFetchButtonLoading,
            isListenButtonEnabled = uiState.isListenButtonEnabled,
            onFetchClicked = onFetchClicked,
            onListenToAppVersionClicked = onListenToAppVersionClicked
        )
    }
}

@Composable
fun RemoteConfigHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.remote_config),
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
        )
        Text(
            text = stringResource(R.string.remote_config_des),
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun FetchSection(
    appVersion: String,
    isFetchButtonLoading: Boolean,
    isListenButtonEnabled: Boolean,
    onFetchClicked: () -> Unit,
    onListenToAppVersionClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onFetchClicked,
            enabled = !isFetchButtonLoading
        ) {
            if (isFetchButtonLoading)
                CircularProgressIndicator()
            else
                Text(text = stringResource(R.string.fetch))
        }

        Button(
            onClick = onListenToAppVersionClicked,
            enabled = isListenButtonEnabled
        ) {
            Text(text = stringResource(R.string.listen_to_app_version_updates))
        }
    }
    Text(
        text = stringResource(R.string.app_version_is, appVersion),
        fontFamily = SfDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Preview(showSystemUi = true)
@Composable
fun RemoteConfigScreenPreview() {
    RemoteConfigScreen()
}