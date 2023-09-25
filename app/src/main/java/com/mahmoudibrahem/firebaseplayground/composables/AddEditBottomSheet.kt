package com.mahmoudibrahem.firebaseplayground.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmoudibrahem.firebaseplayground.R
import com.mahmoudibrahem.firebaseplayground.ui.screens.firestore.FirestoreUiState
import com.mahmoudibrahem.firebaseplayground.ui.theme.MainColor
import com.mahmoudibrahem.firebaseplayground.ui.theme.SfDisplay
import com.mahmoudibrahem.firebaseplayground.util.PossibleFormErrors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditBottomSheet(
    schoolName: String,
    schoolAddress: String,
    isButtonLoading: Boolean,
    errors: List<PossibleFormErrors>,
    onDismissRequest: () -> Unit,
    onSchoolNameChanged: (String) -> Unit,
    onSchoolAddressChanged: (String) -> Unit,
    onSaveBtnClicked: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        BottomSheetContent(
            schoolName = schoolName,
            schoolAddress = schoolAddress,
            errors = errors,
            isButtonLoading = isButtonLoading,
            onSchoolNameChanged = onSchoolNameChanged,
            onSchoolAddressChanged = onSchoolAddressChanged,
            onSaveBtnClicked = onSaveBtnClicked
        )
    }
}

@Composable
private fun BottomSheetContent(
    schoolName: String,
    schoolAddress: String,
    isButtonLoading: Boolean,
    errors: List<PossibleFormErrors>,
    onSchoolNameChanged: (String) -> Unit,
    onSchoolAddressChanged: (String) -> Unit,
    onSaveBtnClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = schoolName,
            onValueChange = onSchoolNameChanged,
            label = { Text(text = stringResource(R.string.school_name)) },
            isError = errors.contains(PossibleFormErrors.INVALID_SCHOOL_NAME),
            supportingText = {
                if (errors.contains(PossibleFormErrors.INVALID_SCHOOL_NAME)) {
                    Text(
                        text = stringResource(R.string.invalid_school_name),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = schoolAddress,
            onValueChange = onSchoolAddressChanged,
            label = { Text(text = stringResource(R.string.school_address)) },
            modifier = Modifier.fillMaxWidth(),
            isError = errors.contains(PossibleFormErrors.INVALID_SCHOOL_ADDRESS),
            supportingText = {
                if (errors.contains(PossibleFormErrors.INVALID_SCHOOL_ADDRESS)) {
                    Text(
                        text = stringResource(R.string.invalid_school_address),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(8.dp)),
            onClick = onSaveBtnClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = MainColor,
                disabledContainerColor = MainColor.copy(alpha = 0.5f)
            ),
            enabled = !isButtonLoading
        ) {
            if (isButtonLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text(
                    text = stringResource(R.string.save),
                    fontFamily = SfDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        }
    }
}