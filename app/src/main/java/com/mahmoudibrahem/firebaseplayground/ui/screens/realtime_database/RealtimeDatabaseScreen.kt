package com.mahmoudibrahem.firebaseplayground.ui.screens.realtime_database

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoudibrahem.firebaseplayground.R
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.ui.theme.MainColor
import com.mahmoudibrahem.firebaseplayground.ui.theme.SfDisplay
import com.mahmoudibrahem.firebaseplayground.util.FirebaseResult
import com.mahmoudibrahem.firebaseplayground.util.PossibleFormErrors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RealtimeDatabaseScreen(
    viewModel: RealtimeDatabaseViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    var isShowSaveSchoolBottomSheet by remember { mutableStateOf(false) }
    var initId by remember { mutableStateOf("") }
    var initName by remember { mutableStateOf("") }
    var initAddress by remember { mutableStateOf("") }

    ObserveUIState(
        upsertState = viewModel.upsertSchoolState.value,
        deleteState = viewModel.deleteSchoolState.value,
        context = context
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isShowSaveSchoolBottomSheet = true
                    initName = ""
                    initAddress = ""
                    initId = ""
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        }
    ) {
        Column {
            Text(
                text = "Realtime Database",
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                modifier = Modifier.padding(top = 16.dp),
            )
            Text(
                text = stringResource(R.string.realtime_des),
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 16.dp),
            )
            Spacer(modifier = Modifier.height(28.dp))
            AnimatedVisibility(visible = viewModel.isLoadingList.value) { CircularProgressIndicator() }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(count = viewModel.schoolList.size) { index ->
                    SchoolItem(
                        school = viewModel.schoolList[index],
                        onEditClicked = { school ->
                            isShowSaveSchoolBottomSheet = true
                            initName = school.name
                            initAddress = school.address
                            initId = school.id
                        },
                        onDeleteClicked = {
                            viewModel.deleteSchool(it)
                        }
                    )
                }
            }
        }
        if (isShowSaveSchoolBottomSheet) {
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
        }

    }
}

@Composable
fun SchoolItem(
    school: School,
    onEditClicked: (School) -> Unit,
    onDeleteClicked: (school: School) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .border(width = 1.dp, color = MainColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterStart),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = school.name,
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Address : ${school.address}",
                fontFamily = SfDisplay,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            )
        }
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    onEditClicked(school)
                },

                ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MainColor
                )
            }
            IconButton(
                onClick = {
                    onDeleteClicked(school)
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Red
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSchoolBottomSheet(
    nameValue: String,
    addressValue: String,
    onSaveBtnClicked: (schoolName: String, schoolAddress: String) -> Unit,
    saveSchoolStateProvider: () -> FirebaseResult<*>,
    fromErrorsStateProvider: () -> List<PossibleFormErrors>,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
    ) {
        var schoolName by remember { mutableStateOf(nameValue) }
        var schoolAddress by remember { mutableStateOf(addressValue) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            TextField(
                value = schoolName,
                onValueChange = { schoolName = it },
                label = { Text(text = "School name") },
                modifier = Modifier.fillMaxWidth(),
                isError = fromErrorsStateProvider().contains(PossibleFormErrors.INVALID_SCHOOL_NAME),
                supportingText = {
                    if (fromErrorsStateProvider().contains(PossibleFormErrors.INVALID_SCHOOL_NAME)) {
                        Text(text = "Invalid school name", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextField(
                value = schoolAddress,
                onValueChange = { schoolAddress = it },
                label = { Text(text = "School Address") },
                modifier = Modifier.fillMaxWidth(),
                isError = fromErrorsStateProvider().contains(PossibleFormErrors.INVALID_SCHOOL_ADDRESS),
                supportingText = {
                    if (fromErrorsStateProvider().contains(PossibleFormErrors.INVALID_SCHOOL_ADDRESS)) {
                        Text(
                            text = "Invalid school Address",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = {
                    onSaveBtnClicked(schoolName, schoolAddress)
                    onDismissRequest()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainColor,
                    disabledContainerColor = MainColor.copy(alpha = 0.5f)
                ),
                enabled = saveSchoolStateProvider() != FirebaseResult.Loading
            ) {
                if (saveSchoolStateProvider() == FirebaseResult.Loading) {
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
}

@Composable
fun ObserveUIState(
    upsertState: FirebaseResult<*>,
    deleteState: FirebaseResult<*>,
    context: Context
) {
    LaunchedEffect(key1 = upsertState) {
        when (upsertState) {
            is FirebaseResult.Failure -> {
                Toast.makeText(
                    context,
                    (upsertState).msg,
                    Toast.LENGTH_SHORT
                ).show()
            }

            is FirebaseResult.Success -> {
                val result =
                    (upsertState).data as String
                Toast.makeText(
                    context,
                    result,
                    Toast.LENGTH_SHORT
                ).show()
            }

            FirebaseResult.Empty -> {}
            FirebaseResult.Loading -> {}
        }
    }
    LaunchedEffect(key1 = deleteState) {
        when (deleteState) {
            is FirebaseResult.Failure -> {
                Toast.makeText(
                    context,
                    (deleteState).msg,
                    Toast.LENGTH_SHORT
                ).show()
            }

            is FirebaseResult.Success -> {
                val result =
                    (deleteState).data as String
                Toast.makeText(
                    context,
                    result,
                    Toast.LENGTH_SHORT
                ).show()
            }

            FirebaseResult.Empty -> {}
            FirebaseResult.Loading -> {}
        }
    }
}


