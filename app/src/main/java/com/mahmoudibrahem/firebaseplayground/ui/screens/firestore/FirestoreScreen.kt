package com.mahmoudibrahem.firebaseplayground.ui.screens.firestore

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.mahmoudibrahem.firebaseplayground.composables.OrderSection
import com.mahmoudibrahem.firebaseplayground.composables.SchoolItem
import com.mahmoudibrahem.firebaseplayground.composables.SearchSection
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.ui.theme.SfDisplay

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
    if (uiState.showBottomSheet) {
        AddEditBottomSheet(
            schoolName = uiState.schoolName,
            schoolAddress = uiState.schoolAddress,
            isButtonLoading = uiState.isButtonLoading,
            errors = uiState.formErrors,
            onDismissRequest = viewModel::onBottomSheetDismiss,
            onSchoolNameChanged = viewModel::onSchoolNameChanged,
            onSchoolAddressChanged = viewModel::onSchoolAddressChanged,
            onSaveBtnClicked = viewModel::onSaveBtnClicked
        )
    }
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
                searchQuery = uiState.searchQuery,
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



@Preview(showSystemUi = true)
@Composable
fun FirestoreScreenPreview() {
    FirestoreScreen()
}