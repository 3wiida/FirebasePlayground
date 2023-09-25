package com.mahmoudibrahem.firebaseplayground.ui.screens.firestore

import androidx.lifecycle.ViewModel
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.repository.firestore_repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.mahmoudibrahem.firebaseplayground.ui.screens.realtime_database.SaveAction
import com.mahmoudibrahem.firebaseplayground.util.PossibleFormErrors

@HiltViewModel
class FirestoreViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FirestoreUiState())
    val uiState = _uiState.asStateFlow()

    init {
        firestoreRepository.observeSchools(
            onError = { error ->
                _uiState.update { it.copy(errorMsg = error?.message.toString()) }
            },
            onUpdate = { snapshot ->
                snapshot?.let {
                    _uiState.update { it.copy(schoolList = snapshot.toObjects(School::class.java)) }
                }
            }
        )
    }

    fun onAddClicked() {
        _uiState.update {
            it.copy(
                showBottomSheet = true,
                schoolName = "",
                schoolAddress = "",
                addOrUpdate = SaveAction.ADD_NEW,
                successMsg = ""
            )
        }
    }

    fun onEditSchoolClicked(school: School) {
        _uiState.update {
            it.copy(
                showBottomSheet = true,
                schoolName = school.name,
                schoolAddress = school.address,
                addOrUpdate = SaveAction.UPDATE,
                schoolToEdit = school,
                successMsg = ""
            )
        }
    }

    fun onDeleteSchoolClicked(school: School) {
        _uiState.update { it.copy(successMsg = "") }
        firestoreRepository.deleteSchool(school)
            .addOnSuccessListener {
                _uiState.update { it.copy(successMsg = "Deleted Successfully") }
            }
            .addOnFailureListener { exception ->
                _uiState.update { it.copy(errorMsg = exception.message.toString()) }
            }
    }

    fun onOrderAscClicked() {
        firestoreRepository.orderSchoolsASC()
            .addOnSuccessListener { snapshot ->
                snapshot?.let {
                    _uiState.update { it.copy(schoolList = snapshot.toObjects(School::class.java)) }
                }
            }
            .addOnFailureListener { exception ->
                _uiState.update { it.copy(errorMsg = exception.message.toString()) }
            }
    }

    fun onOrderDesClicked() {
        firestoreRepository.orderSchoolsDES()
            .addOnSuccessListener { snapshot ->
                snapshot?.let {
                    _uiState.update { it.copy(schoolList = snapshot.toObjects(School::class.java)) }
                }
            }
            .addOnFailureListener { exception ->
                _uiState.update { it.copy(errorMsg = exception.message.toString()) }
            }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _uiState.update { it.copy(searchQuery = newQuery) }
    }

    fun onSearchClicked() {
        firestoreRepository.searchForSchool(query = uiState.value.searchQuery)
            .addOnSuccessListener { snapshot ->
                snapshot?.let {
                    _uiState.update { it.copy(schoolList = snapshot.toObjects(School::class.java)) }
                }
            }
            .addOnFailureListener { exception ->
                _uiState.update { it.copy(errorMsg = exception.message.toString()) }
            }
    }

    fun onBottomSheetDismiss() {
        _uiState.update { it.copy(showBottomSheet = !it.showBottomSheet) }
    }

    fun onSchoolNameChanged(newValue: String) {
        _uiState.update { it.copy(schoolName = newValue) }
    }

    fun onSchoolAddressChanged(newValue: String) {
        _uiState.update { it.copy(schoolAddress = newValue) }
    }

    fun onSaveBtnClicked() {
        isFormValid(
            schoolName = _uiState.value.schoolName,
            schoolAddress = _uiState.value.schoolAddress
        )
        if (_uiState.value.formErrors.isEmpty()) {


            _uiState.update { it.copy(isButtonLoading = true) }
            if (_uiState.value.addOrUpdate == SaveAction.ADD_NEW) {
                firestoreRepository.addSchool(
                    School(
                        name = _uiState.value.schoolName,
                        address = _uiState.value.schoolAddress
                    )
                ).addOnSuccessListener {
                    _uiState.update {
                        it.copy(
                            successMsg = "Added Successfully",
                            isButtonLoading = false,
                            schoolName = "",
                            schoolAddress = ""
                        )
                    }
                    onBottomSheetDismiss()
                }.addOnFailureListener { exception ->
                    _uiState.update {
                        it.copy(
                            errorMsg = exception.message.toString(),
                            isButtonLoading = false
                        )
                    }
                }
            } else {
                firestoreRepository.editSchool(
                    schoolId = _uiState.value.schoolToEdit?.id!!,
                    newName = _uiState.value.schoolName,
                    newAddress = _uiState.value.schoolAddress
                )
                    .addOnSuccessListener {
                        _uiState.update {
                            it.copy(
                                successMsg = "Edited Successfully",
                                isButtonLoading = false,
                                schoolName = "",
                                schoolAddress = "",
                            )
                        }
                        onBottomSheetDismiss()
                    }.addOnFailureListener { exception ->
                        _uiState.update {
                            it.copy(
                                errorMsg = exception.message.toString(),
                                isButtonLoading = false
                            )
                        }
                    }
            }
        }
    }

    private fun isFormValid(schoolName: String, schoolAddress: String) {
        _uiState.value.formErrors.clear()
        if (schoolName.isEmpty())
            _uiState.value.formErrors.add(PossibleFormErrors.INVALID_SCHOOL_NAME)
        if (schoolAddress.isEmpty())
            _uiState.value.formErrors.add(PossibleFormErrors.INVALID_SCHOOL_ADDRESS)
    }

}
