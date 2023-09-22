package com.mahmoudibrahem.firebaseplayground.ui.screens.firestore

import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.util.PossibleFormErrors

data class FirestoreUiState(
    val searchQuery: String = "",
    val showBottomSheet: Boolean = false,
    val schoolList: List<School> = emptyList(),
    val formErrors: List<PossibleFormErrors> = emptyList(),
    val schoolName: String = "",
    val schoolAddress: String = "",
    val errorMsg: String = "",
    val successMsg: String = "",
    val isButtonLoading: Boolean = false,
    val addOrUpdate: SaveAction = SaveAction.ADD_NEW,
    val schoolToEdit: School? = null
)