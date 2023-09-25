package com.mahmoudibrahem.firebaseplayground.ui.screens.cloud_messaging

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmoudibrahem.firebaseplayground.R
import com.mahmoudibrahem.firebaseplayground.ui.theme.SfDisplay

@Composable
fun CloudMessagingScreen() {
    CloudMessagingScreenContent()
}

@Composable
fun CloudMessagingScreenContent() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {
        Text(
            text = stringResource(R.string.cloud_messaging),
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            modifier = Modifier.align(Alignment.TopStart),
        )
        Text(
            text = stringResource(R.string.cloud_messaging_des),
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CloudMessagingScreenPreview() {
    CloudMessagingScreen()
}