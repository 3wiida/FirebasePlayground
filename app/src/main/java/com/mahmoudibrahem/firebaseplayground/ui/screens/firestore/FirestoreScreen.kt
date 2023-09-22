package com.mahmoudibrahem.firebaseplayground.ui.screens.firestore

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
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
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.ui.screens.realtime_database.SchoolItem
import com.mahmoudibrahem.firebaseplayground.ui.theme.MainColor
import com.mahmoudibrahem.firebaseplayground.ui.theme.SfDisplay
import com.mahmoudibrahem.firebaseplayground.util.PossibleFormErrors

@Composable
fun FirestoreScreen(
    viewModel: FirestoreViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val uiState by viewModel.uiState.collectAsState()
    FirestoreContent(
        uiState = uiState,
        onAddClicked = viewModel::onAddClicked,
        onOrderAscClicked = viewModel::onOrderAscClicked,
        onOrderDesClicked = viewModel::onOrderDesClicked,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onSearchClicked = viewModel::onSearchClicked,
        onEditSchoolClicked = viewModel::onEditSchoolClicked,
        onDeleteSchoolClicked = viewModel::onDeleteSchoolClicked
    )
    if(uiState.showBottomSheet){
        BottomSheet(
            uiState = uiState,
            onDismissRequest = viewModel::onBottomSheetDismiss,
            onSchoolNameChanged = viewModel::onSchoolNameChanged,
            onSchoolAddressChanged = viewModel::onSchoolAddressChanged,
            onSaveBtnClicked = viewModel::onSaveBtnClicked
        )
    }
    LaunchedEffect(key1 = uiState.errorMsg) {
        if(uiState.errorMsg.isNotEmpty())
            Toast.makeText(context, uiState.errorMsg, Toast.LENGTH_LONG).show()
    }
    LaunchedEffect(key1 = uiState.successMsg) {
        if(uiState.successMsg.isNotEmpty())
            Toast.makeText(context, uiState.successMsg, Toast.LENGTH_LONG).show()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FirestoreContent(
    uiState: FirestoreUiState,
    onAddClicked: () -> Unit,
    onEditSchoolClicked: (School) -> Unit = {},
    onDeleteSchoolClicked: (School) -> Unit = {},
    onSearchClicked: () -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {},
    onOrderAscClicked: () -> Unit = {},
    onOrderDesClicked: () -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClicked
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            HeaderSection()
            Spacer(modifier = Modifier.height(16.dp))
            OrderSection(
                onOrderAscClicked = onOrderAscClicked,
                onOrderDesClicked = onOrderDesClicked
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchSection(
                uiState = uiState,
                onSearchQueryChanged = onSearchQueryChanged,
                onSearchClicked = onSearchClicked
            )
            Spacer(modifier = Modifier.height(16.dp))
            DataSection(
                data = uiState.schoolList,
                onEditSchoolClicked = onEditSchoolClicked,
                onDeleteSchoolClicked = onDeleteSchoolClicked
            )
        }
    }
}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.cloud_firestore),
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            modifier = Modifier.padding(top = 16.dp),
        )
        Text(
            text = stringResource(R.string.firestore_des),
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp),
        )
    }
}

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    onOrderAscClicked: () -> Unit = {},
    onOrderDesClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { onOrderAscClicked() }
        ) {
            Text(text = stringResource(R.string.order_asc))
        }
        Button(
            onClick = { onOrderDesClicked() }
        ) {
            Text(text = stringResource(R.string.order_des))
        }
    }
}

@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    uiState: FirestoreUiState,
    onSearchQueryChanged: (String) -> Unit = {},
    onSearchClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            modifier = Modifier.weight(2f),
            value = uiState.searchQuery,
            onValueChange = onSearchQueryChanged,
            singleLine = true,
            placeholder = { Text(text = stringResource(R.string.search)) }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(2.dp)),
            onClick = onSearchClicked
        ) {
            Text(text = stringResource(R.string.search))
        }
    }
}

@Composable
fun DataSection(
    modifier: Modifier = Modifier,
    data: List<School>,
    onEditSchoolClicked: (School) -> Unit = {},
    onDeleteSchoolClicked: (School) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = data) { school ->
            SchoolItem(
                school = school,
                onEditClicked = onEditSchoolClicked,
                onDeleteClicked = onDeleteSchoolClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    uiState: FirestoreUiState,
    onDismissRequest: () -> Unit,
    onSchoolNameChanged: (String) -> Unit,
    onSchoolAddressChanged: (String) -> Unit,
    onSaveBtnClicked: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        BottomSheetContent(
            uiState = uiState,
            onSchoolNameChanged = onSchoolNameChanged,
            onSchoolAddressChanged = onSchoolAddressChanged,
            onSaveBtnClicked = onSaveBtnClicked
        )
    }
}


@Composable
fun BottomSheetContent(
    uiState: FirestoreUiState,
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
            value = uiState.schoolName,
            onValueChange = onSchoolNameChanged,
            label = { Text(text = stringResource(R.string.school_name)) },
            isError = uiState.formErrors.contains(PossibleFormErrors.INVALID_SCHOOL_NAME),
            supportingText = {
                if (uiState.formErrors.contains(PossibleFormErrors.INVALID_SCHOOL_NAME)) {
                    Text(
                        text = stringResource(R.string.invalid_school_name),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = uiState.schoolAddress,
            onValueChange = onSchoolAddressChanged,
            label = { Text(text = stringResource(R.string.school_address)) },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.formErrors.contains(PossibleFormErrors.INVALID_SCHOOL_ADDRESS),
            supportingText = {
                if (uiState.formErrors.contains(PossibleFormErrors.INVALID_SCHOOL_ADDRESS)) {
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
            enabled = !uiState.isButtonLoading
        ) {
            if (uiState.isButtonLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text(
                    text = "Save",
                    fontFamily = SfDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun FirestoreScreenPreview() {
    FirestoreScreen()
}