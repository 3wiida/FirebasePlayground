package com.mahmoudibrahem.firebaseplayground.ui.screens.realtime_database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.repository.realtime_db_repository.RealtimeDatabaseRepository
import com.mahmoudibrahem.firebaseplayground.util.PossibleFormErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealtimeDatabaseViewModel @Inject constructor(
    private val databaseRepository: RealtimeDatabaseRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RealtimeDatabaseUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeSchoolUpdates()
    }

    private fun observeSchoolUpdates() {
        databaseRepository.observeSchoolsUpdates { dataSnapshot ->
            val schoolList = mutableListOf<School>()
            viewModelScope.launch {
                dataSnapshot.children.forEach { child ->
                    child.getValue(School::class.java)?.let {
                        schoolList.add(it)
                    }
                    _uiState.update { it.copy(schoolList = schoolList) }
                }
            }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _uiState.update { it.copy(searchQuery = newQuery) }
    }

    fun onSchoolNameChanged(newValue: String) {
        _uiState.update { it.copy(schoolName = newValue) }
    }

    fun onSchoolAddressChanged(newValue: String) {
        _uiState.update { it.copy(schoolAddress = newValue) }
    }

    fun onAddButtonClicked() {
        _uiState.update { it.copy(showBottomSheet = true, schoolName = "", schoolAddress = "") }
    }

    fun onOrderAscClicked() {
        databaseRepository.orderSchoolsASC { list ->
            val schoolList = mutableListOf<School>()
            viewModelScope.launch {
                list.children.forEach { dataSnapShot ->
                    dataSnapShot.getValue(School::class.java)?.let { schoolList.add(it) }
                }
                _uiState.update { it.copy(schoolList = schoolList) }
            }
        }
    }

    fun onOrderDesClicked() {
        databaseRepository.orderSchoolsDES { list ->
            val schoolList = mutableListOf<School>()
            viewModelScope.launch {
                list.children.forEach { dataSnapShot ->
                    dataSnapShot.getValue(School::class.java)?.let { schoolList.add(it) }
                }
                _uiState.update { it.copy(schoolList = schoolList.reversed()) }
            }
        }
    }

    fun onSearchClicked() {
        databaseRepository.searchForSchool(query = _uiState.value.searchQuery) { list ->
            val schoolList = mutableListOf<School>()
            viewModelScope.launch {
                list.children.forEach { dataSnapShot ->
                    dataSnapShot.getValue(School::class.java)?.let { schoolList.add(it) }
                }
                _uiState.update { it.copy(schoolList = schoolList.reversed()) }
            }
        }
    }

    fun onDeleteSchoolClicked(school: School) {
        databaseRepository.deleteSchool(school).addOnSuccessListener {
            _uiState.update { it.copy(successMsg = "Deleted Successfully") }
        }.addOnFailureListener { exception ->
            _uiState.update { it.copy(errorMsg = exception.message.toString()) }
        }
    }

    fun onEditSchoolClicked(school: School) {
        _uiState.update {
            it.copy(
                showBottomSheet = true,
                schoolName = school.name,
                schoolAddress = school.address,
                addOrUpdate = SaveAction.UPDATE,
                schoolToEdit = school.id
            )
        }
    }

    fun onSaveBtnClicked() {
        isFormValid(
            schoolName = _uiState.value.schoolName,
            schoolAddress = _uiState.value.schoolAddress
        )
        if (_uiState.value.formErrors.isEmpty()) {
            _uiState.update { it.copy(isSaveBtnLoading = true, errorMsg = "", successMsg = "") }
            if (_uiState.value.addOrUpdate == SaveAction.ADD_NEW) {
                databaseRepository.addSchool(
                    School(
                        name = _uiState.value.schoolName,
                        address = _uiState.value.schoolAddress
                    )
                )?.addOnSuccessListener {
                    _uiState.update {
                        it.copy(
                            successMsg = "Added Successfully",
                            isSaveBtnLoading = false,
                            schoolName = "",
                            schoolAddress = ""
                        )
                    }
                    onBottomSheetDismiss()
                }?.addOnFailureListener { exception ->
                    _uiState.update {
                        it.copy(
                            errorMsg = exception.message.toString(),
                            isSaveBtnLoading = false
                        )
                    }
                }
            } else {
                databaseRepository.editSchool(
                    schoolId = _uiState.value.schoolToEdit,
                    newName = _uiState.value.schoolName,
                    newAddress = _uiState.value.schoolAddress
                )
                    .addOnSuccessListener {
                        _uiState.update {
                            it.copy(
                                successMsg = "Edited Successfully",
                                isSaveBtnLoading = false,
                                schoolName = "",
                                schoolAddress = "",
                            )
                        }
                        onBottomSheetDismiss()
                    }.addOnFailureListener { exception ->
                        _uiState.update {
                            it.copy(
                                errorMsg = exception.message.toString(),
                                isSaveBtnLoading = false
                            )
                        }
                    }
            }
        }
    }

    fun onBottomSheetDismiss() {
        _uiState.update { it.copy(showBottomSheet = false) }
    }


    private fun isFormValid(schoolName: String, schoolAddress: String) {
        _uiState.value.formErrors.clear()
        if (schoolName.isEmpty())
            _uiState.value.formErrors.add(PossibleFormErrors.INVALID_SCHOOL_NAME)
        if (schoolAddress.isEmpty())
            _uiState.value.formErrors.add(PossibleFormErrors.INVALID_SCHOOL_ADDRESS)
    }

}

