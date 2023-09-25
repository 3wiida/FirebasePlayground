package com.mahmoudibrahem.firebaseplayground.ui.screens.realtime_database

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.util.PossibleFormErrors

data class RealtimeDatabaseUiState(
    val schoolList: List<School> = emptyList(),
    val schoolName: String = "",
    val schoolAddress: String = "",
    val showBottomSheet: Boolean = false,
    val searchQuery: String = "",
    val addOrUpdate: SaveAction = SaveAction.ADD_NEW,
    val successMsg: String = "",
    val errorMsg: String = "",
    val isSaveBtnLoading: Boolean = false,
    val formErrors: SnapshotStateList<PossibleFormErrors> = mutableStateListOf(),
    val schoolToEdit: String = ""
)

enum class SaveAction {
    UPDATE,
    ADD_NEW
}