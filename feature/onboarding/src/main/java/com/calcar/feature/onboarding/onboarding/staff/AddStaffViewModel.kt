package com.calcar.feature.onboarding.onboarding.staff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calcar.common.core.result.onFailure
import com.calcar.common.core.result.onSuccess
import com.calcar.common.domain.entities.Profession
import com.calcar.common.domain.usecases.SaveDirectStaffInput
import com.calcar.common.domain.usecases.SaveDirectStaffUseCase
import com.calcar.common.ui.navigation.Navigator
import com.calcar.feature.onboarding.ui.models.ProfessionUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class AddStaffViewModel(
    private val navigator: Navigator,
    private val saveDirectStaffUseCase: SaveDirectStaffUseCase,
) : ViewModel() {

    private val _step = MutableStateFlow(AddStaffFormStep.Selection)
    val step: StateFlow<AddStaffFormStep> = _step.asStateFlow()

    private val _selectedProfession = MutableStateFlow(ProfessionUi.Mechanic)
    val selectedProfession: StateFlow<ProfessionUi> = _selectedProfession.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName.asStateFlow()

    private val _salary = MutableStateFlow("")
    val salary: StateFlow<String> = _salary.asStateFlow()

    private val _agreementHours = MutableStateFlow("")
    val agreementHours: StateFlow<String> = _agreementHours.asStateFlow()

    fun onCloseForm() {
        navigator.navigateUp()
    }

    fun onBackToSelection() {
        _step.update { AddStaffFormStep.Selection }
    }

    fun onContinueToForm() {
        _step.update { AddStaffFormStep.Form }
    }

    fun onSelectProfession(newProfession: ProfessionUi) {
        _selectedProfession.update { newProfession }
    }

    fun onNameChanged(newValue: String) {
        _name.update { newValue }
    }

    fun onLastNameChanged(newValue: String) {
        _lastName.update { newValue }
    }

    fun onSalaryChanged(newValue: String) {
        _salary.update { newValue }
    }

    fun onAgreementHoursChanged(newValue: String) {
        _agreementHours.update { newValue }
    }

    fun onSave() {
        viewModelScope.launch {
            if (_selectedProfession.value.isIndirect()) {
                // TODO
            } else {
                saveDirectStaff()
                    .onSuccess {  }
                    .onFailure {  }
            }
        }
    }

    fun onSaveAndAddOther() {
        viewModelScope.launch {
            if (_selectedProfession.value.isIndirect()) {
                // TODO
            } else {
                saveDirectStaff()
                    .onSuccess {  }
                    .onFailure {  }
            }
        }
    }

    private suspend fun saveDirectStaff() = saveDirectStaffUseCase(
        SaveDirectStaffInput(
            name = _name.value,
            lastName = _lastName.value,
            salary = _salary.value,
            hours = _agreementHours.value,
            profession = _selectedProfession.value.toDomain()
        )
    )
}

enum class AddStaffFormStep {
    Selection, Form
}

private fun ProfessionUi.toDomain() = when (this) {
    ProfessionUi.Mechanic -> Profession.Mechanic
    ProfessionUi.Painter -> Profession.Painter
    ProfessionUi.Platter -> Profession.Platter
    ProfessionUi.Preparer -> Profession.Preparer
    ProfessionUi.Manager -> Profession.Manager
    ProfessionUi.Administrative -> Profession.Administrative
}