package com.mahmoudibrahem.firebaseplayground.ui.screens.auth.email_password

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoudibrahem.firebaseplayground.R
import com.mahmoudibrahem.firebaseplayground.composables.VerticalSpacer16
import com.mahmoudibrahem.firebaseplayground.composables.VerticalSpacer28
import com.mahmoudibrahem.firebaseplayground.composables.VerticalSpacer32
import com.mahmoudibrahem.firebaseplayground.ui.theme.SfDisplay
import com.mahmoudibrahem.firebaseplayground.util.PossibleFormErrors

@Composable
fun EmailPasswordScreen(
    viewModel: EmailPasswordRegisterViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val uiState by viewModel.uiState.collectAsState()
    EmailPasswordScreenContent(
        uiState = uiState,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onRegisterClicked = viewModel::onRegisterClicked,
        onLoginClicked = viewModel::onLoginClicked
    )
    LaunchedEffect(key1 = uiState.successMsg){
        if(uiState.successMsg.isNotEmpty())
            Toast.makeText(context, uiState.successMsg, Toast.LENGTH_LONG).show()
    }
    LaunchedEffect(key1 = uiState.errorMsg){
        if(uiState.errorMsg.isNotEmpty())
            Toast.makeText(context, uiState.errorMsg, Toast.LENGTH_LONG).show()
    }
}

@Composable
fun EmailPasswordScreenContent(
    uiState: EmailPasswordUIState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRegisterClicked: () -> Unit,
    onLoginClicked: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputSection(
            email = uiState.email,
            password = uiState.password,
            errors = uiState.errors,
            onEmailChanged = onEmailChanged,
            onPasswordChanged = onPasswordChanged
        )
        VerticalSpacer32()
        ActionSection(
            onRegisterClicked = onRegisterClicked,
            isRegisterLoading = uiState.isRegisterLoading,
            onLoginClicked = onLoginClicked,
            isLoginLoading = uiState.isLoginLoading
        )
    }
}

@Composable
fun InputSection(
    email: String,
    onEmailChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    errors: List<PossibleFormErrors>
) {
    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = onEmailChanged,
            isError = errors.contains(PossibleFormErrors.EMAIL_INVALID),
            label = { Text(text = stringResource(R.string.e_mail)) },
            supportingText = {
                if (errors.contains(PossibleFormErrors.EMAIL_INVALID)) {
                    Text(
                        text = stringResource(R.string.invalid_email_address),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        VerticalSpacer16()
        TextField(
            value = password,
            onValueChange = onPasswordChanged,
            label = { Text(text = stringResource(R.string.password)) },
            modifier = Modifier.fillMaxWidth(),
            isError = errors.contains(PossibleFormErrors.PASSWORD_INVALID),
            supportingText = {
                if (errors.contains(PossibleFormErrors.PASSWORD_INVALID)) {
                    Text(
                        text = stringResource(R.string.invalid_password),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
    }
}

@Composable
fun ActionSection(
    onRegisterClicked: () -> Unit,
    isRegisterLoading: Boolean,
    onLoginClicked: () -> Unit,
    isLoginLoading: Boolean
) {
    Button(
        modifier = Modifier
            .width(200.dp)
            .height(52.dp)
            .clip(RoundedCornerShape(8.dp)),
        onClick = onRegisterClicked,
        enabled = !isRegisterLoading
    ) {
        if (isRegisterLoading) {
            CircularProgressIndicator(color = Color.White)
        } else {
            Text(
                text = stringResource(R.string.register),
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
    }
    VerticalSpacer28()
    Button(
        modifier = Modifier
            .width(200.dp)
            .height(52.dp)
            .clip(RoundedCornerShape(8.dp)),
        onClick = onLoginClicked,
        enabled = !isLoginLoading
    ) {
        if (isLoginLoading) {
            CircularProgressIndicator(color = Color.White)
        } else {
            Text(
                text = stringResource(R.string.login),
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun EmailPasswordScreenPreview() {
    EmailPasswordScreen()
}