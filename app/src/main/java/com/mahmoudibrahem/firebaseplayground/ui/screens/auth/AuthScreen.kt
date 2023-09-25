package com.mahmoudibrahem.firebaseplayground.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoudibrahem.firebaseplayground.composables.VerticalSpacer8
import com.mahmoudibrahem.firebaseplayground.pojo.AuthMethod
import com.mahmoudibrahem.firebaseplayground.ui.theme.MainColor
import com.mahmoudibrahem.firebaseplayground.ui.theme.SfDisplay
import com.mahmoudibrahem.firebaseplayground.util.shadow

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToEmailPassword: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    AuthScreenContent(
        authMethods = uiState.methods,
        onMethodClicked = { methodId ->
            when (methodId) {
                1 -> onNavigateToEmailPassword()
            }
        }
    )
}

@Composable
fun AuthScreenContent(
    authMethods: List<AuthMethod>,
    onMethodClicked: (id: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = authMethods) { method ->
            AuthMethod(
                authMethod = method,
                onMethodClicked = onMethodClicked
            )
        }
    }
}

@Composable
fun AuthMethod(
    authMethod: AuthMethod,
    onMethodClicked: (id: Int) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(color = Color.Black.copy(0.2f), blurRadius = 10.dp)
            .background(
                color = MainColor.copy(alpha = 0.8f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onMethodClicked(authMethod.id)
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = authMethod.image),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        VerticalSpacer8()
        Text(
            text = authMethod.name,
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen()
}