package com.mahmoudibrahem.firebaseplayground.ui.screens.realtime_database

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ktx.getValue
import com.mahmoudibrahem.firebaseplayground.pojo.School
import com.mahmoudibrahem.firebaseplayground.repository.realtime_db_repository.RealtimeDatabaseRepository
import com.mahmoudibrahem.firebaseplayground.util.FirebaseResult
import com.mahmoudibrahem.firebaseplayground.util.PossibleFormErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealtimeDatabaseViewModel @Inject constructor(
    private val databaseRepository: RealtimeDatabaseRepository
) : ViewModel() {

    val upsertSchoolState = mutableStateOf<FirebaseResult<*>>(FirebaseResult.Empty)
    val deleteSchoolState = mutableStateOf<FirebaseResult<*>>(FirebaseResult.Empty)
    val schoolList = mutableStateListOf<School>()
    val formErrors = mutableStateListOf<PossibleFormErrors>()
    var isLoadingList = mutableStateOf(true)

    init {
        observeSchoolUpdates()
    }

    fun upsertSchool(school: School, action: SaveAction) {
        upsertSchoolState.value = FirebaseResult.Loading
        val task = databaseRepository.upsertSchool(
            school = School(
                id = school.id,
                name = school.name,
                address = school.address
            ),
            action = action
        )
        if (task == null) {
            upsertSchoolState.value =
                FirebaseResult.Failure(msg = "Can't create id for school right now, try again later")
        } else {
            task.addOnSuccessListener {
                upsertSchoolState.value = FirebaseResult.Success(data = "School Added Successfully")
            }.addOnFailureListener {
                upsertSchoolState.value = FirebaseResult.Failure(msg = it.message.toString())

            }
        }
    }

    fun deleteSchool(school: School) {
        deleteSchoolState.value = FirebaseResult.Loading
        databaseRepository.deleteSchool(school).addOnSuccessListener {
            deleteSchoolState.value = FirebaseResult.Success(data = "Deleted Successfully")
        }.addOnFailureListener {
            deleteSchoolState.value = FirebaseResult.Failure(msg = it.message.toString())
        }
    }

    private fun observeSchoolUpdates() {
        databaseRepository.observeSchoolsUpdates { dataSnapshot ->
            schoolList.clear()
            viewModelScope.launch {
                dataSnapshot.children.forEach { child ->
                    child.getValue<School>()?.let {
                        schoolList.add(it)
                    }
                }
                isLoadingList.value = false
            }
        }
    }

    fun isFormValid(schoolName: String, schoolAddress: String) {
        formErrors.clear()
        if (schoolName.isEmpty())
            formErrors.add(PossibleFormErrors.INVALID_SCHOOL_NAME)
        if (schoolAddress.isEmpty())
            formErrors.add(PossibleFormErrors.INVALID_SCHOOL_ADDRESS)
    }

}

enum class SaveAction {
    UPDATE,
    ADD_NEW
}