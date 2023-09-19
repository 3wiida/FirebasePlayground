package com.mahmoudibrahem.firebaseplayground.ui.screens.auth

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.IntentSender
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mahmoudibrahem.firebaseplayground.R
import com.mahmoudibrahem.firebaseplayground.pojo.AuthMethod
import com.mahmoudibrahem.firebaseplayground.ui.theme.MainColor
import com.mahmoudibrahem.firebaseplayground.ui.theme.SfDisplay
import com.mahmoudibrahem.firebaseplayground.util.shadow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToEmailPassword: () -> Unit,
    context: Context = LocalContext.current,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = viewModel.getAuthMethods()) { method ->
            AuthMethod(
                authMethod = method,
                onMethodClicked = { id ->
                    when (id) {
                        1 -> onNavigateToEmailPassword()
                    }
                }
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
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = authMethod.name, fontFamily = SfDisplay,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}