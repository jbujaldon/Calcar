package com.calcar.feature.onboarding.onboarding.pages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpense
import com.calcar.common.domain.semifixexpenses.usecases.GetSavedSemiFixExpensesUseCase
import com.calcar.common.domain.staff.entities.Profession
import com.calcar.common.domain.staff.entities.Staff
import com.calcar.common.domain.staff.entities.StaffId
import com.calcar.common.domain.staff.usecases.DeleteStaffUseCase
import com.calcar.common.domain.staff.usecases.GetAllStaffUseCase
import com.calcar.common.ui.navigation.Navigator
import com.calcar.feature.onboarding.navigation.OnboardingAddStaffDestination
import com.calcar.common.ui.models.StaffIdUi
import com.calcar.common.ui.models.StaffUi
import com.calcar.common.ui.models.ProfessionUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class OnboardingViewModel(
    private val navigator: Navigator,
    private val deleteStaffUseCase: DeleteStaffUseCase,
    getAllStaffUseCase: GetAllStaffUseCase,
    getSavedSemiFixExpensesUseCase: GetSavedSemiFixExpensesUseCase,
) : ViewModel() {

    private val _currentPage = MutableStateFlow(OnboardingPageUi.Staff)
    val currentPage: StateFlow<OnboardingPageUi> = _currentPage.asStateFlow()

    val staffList: StateFlow<List<StaffUi>> = getAllStaffUseCase(Unit)
        .map { it.map(Staff::toStaffUi) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val semiFixExpenses: StateFlow<List<SemiFixExpense>> = getSavedSemiFixExpensesUseCase(Unit)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val isPreviousButtonVisible: StateFlow<Boolean> = _currentPage
        .map { it != OnboardingPageUi.Staff }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), false)

    val isNextButtonEnabled: StateFlow<Boolean> = combine (
        _currentPage,
        staffList
    ) { onboardingPage, staffList ->
        when (onboardingPage) {
            OnboardingPageUi.Staff -> staffList.isNotEmpty()
            OnboardingPageUi.SemiFixExpenses -> false
            else -> false
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), false)

    fun onAddStaff() {
        navigator.navigateTo(OnboardingAddStaffDestination())
    }

    fun onDeleteStaff(id: StaffIdUi) {
        viewModelScope.launch {
            deleteStaffUseCase(StaffId.Id(id.value))
        }
    }

    fun onAddSemiFixExpense() {

    }

    fun onNextPage() {
        when (_currentPage.value) {
            OnboardingPageUi.Staff -> updateScreenPage(OnboardingPageUi.SemiFixExpenses)
            OnboardingPageUi.SemiFixExpenses -> Unit
            else -> Unit
        }
    }

    fun onPreviousPage() {
        when (_currentPage.value) {
            OnboardingPageUi.Staff -> Unit
            OnboardingPageUi.SemiFixExpenses -> updateScreenPage(OnboardingPageUi.Staff)
            else -> Unit
        }
    }

    private fun updateScreenPage(newContent: OnboardingPageUi) {
        _currentPage.value = newContent
    }
}

private fun Staff.toStaffUi(): StaffUi = StaffUi(
    id = StaffIdUi(id.value),
    fullName = "${fullName.name} ${fullName.lastName}",
    salary = salary.value,
    profession = profession.toProfessionUi(),
)

private fun Profession.toProfessionUi(): ProfessionUi = when (this) {
    Profession.Mechanic -> ProfessionUi.Mechanic
    Profession.Painter -> ProfessionUi.Painter
    Profession.Platter -> ProfessionUi.Platter
    Profession.Preparer -> ProfessionUi.Preparer
    Profession.Manager -> ProfessionUi.Manager
    Profession.Administrative -> ProfessionUi.Administrative
}
