package com.mahmoudibrahem.firebaseplayground.ui.screens.auth.email_password

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoudibrahem.firebaseplayground.ui.theme.MainColor
import com.mahmoudibrahem.firebaseplayground.ui.theme.SfDisplay
import com.mahmoudibrahem.firebaseplayground.util.FirebaseResult
import com.mahmoudibrahem.firebaseplayground.util.PossibleFormErrors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailPasswordRegisterScreen(
    viewModel: EmailPasswordRegisterViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    LaunchedEffect(key1 = viewModel.registerState.value) {
        when (viewModel.registerState.value) {
            is FirebaseResult.Failure -> {
                Toast.makeText(
                    context,
                    (viewModel.registerState.value as FirebaseResult.Failure).msg,
                    Toast.LENGTH_SHORT
                ).show()
            }

            is FirebaseResult.Success -> {
                val result =
                    (viewModel.registerState.value as FirebaseResult.Success<*>).data as String
                Toast.makeText(
                    context,
                    "Success UID = $result",
                    Toast.LENGTH_SHORT
                ).show()
            }

            FirebaseResult.Empty -> {}
            FirebaseResult.Loading -> {}
        }
    }

    LaunchedEffect(key1 = viewModel.loginState.value) {
        when (viewModel.loginState.value) {
            is FirebaseResult.Failure -> {
                Toast.makeText(
                    context,
                    (viewModel.loginState.value as FirebaseResult.Failure).msg,
                    Toast.LENGTH_SHORT
                ).show()
            }

            is FirebaseResult.Success -> {
                val result =
                    (viewModel.loginState.value as FirebaseResult.Success<*>).data as String
                Toast.makeText(
                    context,
                    "Success UID = $result",
                    Toast.LENGTH_SHORT
                ).show()
            }

            FirebaseResult.Empty -> {}
            FirebaseResult.Loading -> {}
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = viewModel.email.value,
            onValueChange = { viewModel.email.value = it },
            label = { Text(text = "E-mail") },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.formErrors.contains(PossibleFormErrors.EMAIL_INVALID),
            supportingText = {
                if (viewModel.formErrors.contains(PossibleFormErrors.EMAIL_INVALID)) {
                    Text(text = "Invalid Email Address", color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = viewModel.password.value,
            onValueChange = { viewModel.password.value = it },
            label = { Text(text = "Password") },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.formErrors.contains(PossibleFormErrors.PASSWORD_INVALID),
            supportingText = {
                if (viewModel.formErrors.contains(PossibleFormErrors.PASSWORD_INVALID)) {
                    Text(text = "Invalid Password", color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = {
                viewModel.registerUser()
            },
            modifier = Modifier
                .width(200.dp)
                .height(52.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainColor,
                disabledContainerColor = MainColor.copy(alpha = 0.5f)
            ),
            enabled = viewModel.registerState.value != FirebaseResult.Loading
        ) {

            if (viewModel.registerState.value == FirebaseResult.Loading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text(
                    text = "Register",
                    fontFamily = SfDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = {
                viewModel.loginUser()
            },
            modifier = Modifier
                .width(200.dp)
                .height(52.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainColor,
                disabledContainerColor = MainColor.copy(alpha = 0.5f)
            ),
            enabled = viewModel.loginState.value != FirebaseResult.Loading
        ) {
            if (viewModel.loginState.value == FirebaseResult.Loading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text(
                    text = "Login",
                    fontFamily = SfDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        }
    }

}