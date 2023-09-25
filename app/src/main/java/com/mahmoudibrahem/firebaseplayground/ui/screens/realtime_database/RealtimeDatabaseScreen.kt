package com.mahmoudibrahem.firebaseplayground.ui.screens.realtime_database

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.mahmoudibrahem.firebaseplayground.composables.AddEditBottomSheet
import com.mahmoudibrahem.firebaseplayground.composables.DataSection
import com.mahmoudibrahem.firebaseplayground.composables.OrderSection
import com.mahmoudibrahem.firebaseplayground.composables.SearchSection
import com.mahmoudibrahem.firebaseplayground.composables.VerticalSpacer16
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.ui.theme.SfDisplay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RealtimeDatabaseScreen(
    viewModel: RealtimeDatabaseViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val uiState by viewModel.uiState.collectAsState()
    ScreenContent(
        uiState = uiState,
        onAddButtonClicked = viewModel::onAddButtonClicked,
        onOrderAscClicked = viewModel::onOrderAscClicked,
        onOrderDesClicked = viewModel::onOrderDesClicked,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onSearchClicked = viewModel::onSearchClicked,
        onDeleteSchoolClicked = viewModel::onDeleteSchoolClicked,
        onEditSchoolClicked = viewModel::onEditSchoolClicked
    )
    if (uiState.showBottomSheet)
        AddEditBottomSheet(
            schoolName = uiState.schoolName,
            schoolAddress = uiState.schoolAddress,
            isButtonLoading = uiState.isSaveBtnLoading,
            errors = uiState.formErrors,
            onDismissRequest = viewModel::onBottomSheetDismiss,
            onSchoolNameChanged = viewModel::onSchoolNameChanged,
            onSchoolAddressChanged = viewModel::onSchoolAddressChanged,
            onSaveBtnClicked = viewModel::onSaveBtnClicked
        )
    LaunchedEffect(key1 = uiState.errorMsg) {
        if (uiState.errorMsg.isNotEmpty())
            Toast.makeText(context, uiState.errorMsg, Toast.LENGTH_LONG).show()
    }
    LaunchedEffect(key1 = uiState.successMsg) {
        if (uiState.successMsg.isNotEmpty())
            Toast.makeText(context, uiState.successMsg, Toast.LENGTH_LONG).show()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun ScreenContent(
    uiState: RealtimeDatabaseUiState,
    onAddButtonClicked: () -> Unit,
    onOrderAscClicked: () -> Unit,
    onOrderDesClicked: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onSearchClicked: () -> Unit,
    onDeleteSchoolClicked: (School) -> Unit,
    onEditSchoolClicked: (School) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddButtonClicked
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = ""
                )
            }
        }
    ) {
        Column {
            ScreenHeader()
            VerticalSpacer16()
            OrderSection(
                onOrderAscClicked = onOrderAscClicked,
                onOrderDesClicked = onOrderDesClicked
            )
            VerticalSpacer16()
            SearchSection(
                searchQuery = uiState.searchQuery,
                onSearchQueryChanged = onSearchQueryChanged,
                onSearchClicked = onSearchClicked
            )
            VerticalSpacer16()
            DataSection(
                data = uiState.schoolList,
                onDeleteSchoolClicked = onDeleteSchoolClicked,
                onEditSchoolClicked = onEditSchoolClicked
            )
            /*if (isShowSaveSchoolBottomSheet) {
            AddSchoolBottomSheet(
                nameValue = initName,
                addressValue = initAddress,
                saveSchoolStateProvider = { viewModel.upsertSchoolState.value },
                fromErrorsStateProvider = { viewModel.formErrors },
                onSaveBtnClicked = { schoolName, schoolAddress ->
                    viewModel.isFormValid(schoolName, schoolAddress)
                    if (viewModel.formErrors.isEmpty()) {
                        if (initId.isEmpty()) {
                            viewModel.upsertSchool(
                                school = School(
                                    id = initId,
                                    name = schoolName,
                                    address = schoolAddress
                                ),
                                action = SaveAction.ADD_NEW
                            )
                        } else {
                            viewModel.upsertSchool(
                                school = School(
                                    id = initId,
                                    name = schoolName,
                                    address = schoolAddress
                                ),
                                action = SaveAction.UPDATE
                            )
                        }
                    }
                },
                onDismissRequest = {
                    isShowSaveSchoolBottomSheet = false
                }
            )
        }*/
        }
    }
}

@Composable
private fun ScreenHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.realtime_database),
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        )
        Text(
            text = stringResource(R.string.realtime_des),
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RealtimeDatabaseScreenPreview() {
    RealtimeDatabaseScreen()
}