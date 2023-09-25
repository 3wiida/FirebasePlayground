package com.mahmoudibrahem.firebaseplayground.ui.screens.firestore

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.util.PossibleFormErrors
import com.mahmoudibrahem.firebaseplayground.ui.screens.realtime_database.SaveAction

data class FirestoreUiState(
    val searchQuery: String = "",
    val showBottomSheet: Boolean = false,
    val schoolList: List<School> = emptyList(),
    val formErrors: SnapshotStateList<PossibleFormErrors> = mutableStateListOf(),
    val schoolName: String = "",
    val schoolAddress: String = "",
    val errorMsg: String = "",
    val successMsg: String = "",
    val isButtonLoading: Boolean = false,
    val addOrUpdate: SaveAction = SaveAction.ADD_NEW,
    val schoolToEdit: School? = null
)