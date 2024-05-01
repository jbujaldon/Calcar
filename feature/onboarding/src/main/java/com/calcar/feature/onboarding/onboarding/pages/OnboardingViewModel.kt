package com.calcar.feature.onboarding.onboarding.pages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calcar.common.ui.navigation.Navigator
import com.calcar.feature.onboarding.navigation.OnboardingAddStaffDestination
import com.calcar.feature.onboarding.ui.models.StaffId
import com.calcar.feature.onboarding.ui.models.StaffUi
import com.calcar.feature.onboarding.ui.models.ProfessionUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class OnboardingViewModel(
    private val navigator: Navigator,
) : ViewModel() {

    private val _currentPage = MutableStateFlow(OnboardingPageUi.Staff)
    val currentPage: StateFlow<OnboardingPageUi> = _currentPage.asStateFlow()

    val staffList: StateFlow<List<StaffUi>> = MutableStateFlow(
        listOf(
            StaffUi(
                id = StaffId(1L),
                fullName = "Nombre Apellidos",
                salary = 1500.0,
                profession = ProfessionUi.Mechanic,
            ),
            StaffUi(
                id = StaffId(2L),
                fullName = "Nombre Apellidos",
                salary = 1500.0,
                profession = ProfessionUi.Painter,
            ),
            StaffUi(
                id = StaffId(3L),
                fullName = "Nombre Apellidos",
                salary = 1500.0,
                profession = ProfessionUi.Platter,
            ),
        )
    )

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
