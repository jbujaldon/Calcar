package com.calcar.feature.onboarding.onboarding.pages

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calcar.common.core.result.AppResult
import com.calcar.common.core.result.andThen
import com.calcar.common.core.result.onFailure
import com.calcar.common.core.result.onSuccess
import com.calcar.common.core.wrappers.TextWrapper
import com.calcar.common.domain.garage.usecases.GarageInformationInput
import com.calcar.common.domain.garage.usecases.SaveGarageInformationUseCase
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpense
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpenseId
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpenseOption
import com.calcar.common.domain.semifixexpenses.usecases.DeleteSemiFixExpenseUseCase
import com.calcar.common.domain.semifixexpenses.usecases.GetAvailableSemiFixExpenseOptionsUseCase
import com.calcar.common.domain.semifixexpenses.usecases.GetSavedSemiFixExpensesUseCase
import com.calcar.common.domain.semifixexpenses.usecases.SaveSemiFixExpenseInput
import com.calcar.common.domain.semifixexpenses.usecases.SaveSemiFixExpenseUseCase
import com.calcar.common.domain.staff.entities.Profession
import com.calcar.common.domain.staff.entities.Staff
import com.calcar.common.domain.staff.entities.StaffId
import com.calcar.common.domain.staff.usecases.DeleteStaffUseCase
import com.calcar.common.domain.staff.usecases.GetAllStaffUseCase
import com.calcar.common.domain.user.usecases.CompleteOnboardingUseCase
import com.calcar.common.ui.navigation.Navigator
import com.calcar.feature.onboarding.navigation.OnboardingAddStaffDestination
import com.calcar.common.ui.models.StaffIdUi
import com.calcar.common.ui.models.StaffUi
import com.calcar.common.ui.models.ProfessionUi
import com.calcar.common.ui.models.SemiFixExpenseOptionUi
import com.calcar.common.ui.snackbar.SnackbarState
import com.calcar.feature.onboarding.R
import com.calcar.feature.vehicles.navigation.VehiclesDestination
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
    getAvailableSemiFixExpenseOptionsUseCase: GetAvailableSemiFixExpenseOptionsUseCase,
    private val saveSemiFixExpenseUseCase: SaveSemiFixExpenseUseCase,
    private val deleteSemiFixExpenseUseCase: DeleteSemiFixExpenseUseCase,
    private val saveGarageInformationUseCase: SaveGarageInformationUseCase,
    private val completeOnboardingUseCase: CompleteOnboardingUseCase,
) : ViewModel() {

    private val _currentPage = MutableStateFlow(OnboardingPageUi.Staff)
    val currentPage: StateFlow<OnboardingPageUi> = _currentPage.asStateFlow()

    val staffList: StateFlow<List<StaffUi>> = getAllStaffUseCase(Unit)
        .map { it.map(Staff::toStaffUi) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val savedSemiFixExpenses: StateFlow<List<SemiFixExpense>> = getSavedSemiFixExpensesUseCase(Unit)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val isPreviousButtonVisible: StateFlow<Boolean> = _currentPage
        .map { it != OnboardingPageUi.Staff }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), false)

    private val _showSemiFixExpenseForm = MutableStateFlow(false)
    val showSemiFixExpenseForm: StateFlow<Boolean> = _showSemiFixExpenseForm.asStateFlow()

    private val _selectedExpenseOption = MutableStateFlow<SemiFixExpenseOptionUi?>(null)
    val selectedExpenseOption: StateFlow<SemiFixExpenseOptionUi?> = _selectedExpenseOption.asStateFlow()

    private val _expenseAmountInput = MutableStateFlow("")
    val expenseAmountInput: StateFlow<String> = _expenseAmountInput.asStateFlow()

    private val _objectiveMarginInput = MutableStateFlow("")
    val objectiveMarginInput: StateFlow<String> = _objectiveMarginInput.asStateFlow()

    private val _fillerInput = MutableStateFlow("")
    val fillerInput: StateFlow<String> = _fillerInput.asStateFlow()

    private val _paintInput = MutableStateFlow("")
    val paintInput: StateFlow<String> = _paintInput.asStateFlow()

    private val _varnishInput = MutableStateFlow("")
    val varnishInput: StateFlow<String> = _varnishInput.asStateFlow()

    private val _snackbarState = MutableStateFlow(SnackbarState())
    val snackbarState: StateFlow<SnackbarState> = _snackbarState.asStateFlow()

    private val isGarageInformationValid: StateFlow<Boolean> = combine(
        _objectiveMarginInput,
        _fillerInput,
        _paintInput,
        _varnishInput,
    ) { objectiveMargin, filler, paint, varnish ->
        objectiveMargin.isNotEmpty() && filler.isNotEmpty() && paint.isNotEmpty()
                && varnish.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), false)

    val isNextButtonEnabled: StateFlow<Boolean> = combine(
        _currentPage,
        staffList,
        savedSemiFixExpenses,
        isGarageInformationValid,
    ) { onboardingPage, staffList, semiFixExpenses, isGarageInformationValid ->
        isNextPageEnabled(
            onboardingPage = onboardingPage,
            staffList = staffList,
            semiFixExpenses = semiFixExpenses,
            isGarageInformationValid = isGarageInformationValid,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), false)

    val enableSaveSemiFixExpense: StateFlow<Boolean> = combine(
        _selectedExpenseOption,
        _expenseAmountInput
    ) { selectedName, amount ->
        selectedName != null && amount.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), false)

    val semiFixExpenseOptions: StateFlow<List<SemiFixExpenseOptionUi>> =
        getAvailableSemiFixExpenseOptionsUseCase(Unit)
            .map { it.map(SemiFixExpenseOption::toSemiFixExpenseOptionUi) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), emptyList())

    fun onAddStaff() {
        navigator.navigateTo(OnboardingAddStaffDestination())
    }

    fun onDeleteStaff(id: StaffIdUi) {
        viewModelScope.launch {
            deleteStaffUseCase(StaffId.Id(id.value))
        }
    }

    fun onShowAddSemiFixExpenseForm() {
        _showSemiFixExpenseForm.value = true
    }

    fun closeSemiFixExpenseForm() {
        _showSemiFixExpenseForm.value = false
    }

    fun onSelectExpenseOption(option: SemiFixExpenseOptionUi) {
        _selectedExpenseOption.value = option
    }

    fun onSemiFixExpenseAmountChanged(amount: String) {
        _expenseAmountInput.value = amount
    }

    fun onSaveSemiFixExpense() {
        viewModelScope.launch {
            saveSemiFixExpense()
                .onSuccess { clearAndCloseSemiFixExpenseForm() }
                .onFailure { showSaveSemiFixExpenseError() }
        }
    }

    fun onSaveAndAddOtherSemiFixExpense() {
        viewModelScope.launch {
            saveSemiFixExpense()
                .onSuccess { clearSemiFixExpensesFormInputs() }
                .onFailure { showSaveSemiFixExpenseError() }
        }
    }

    fun onDeleteSemiFixExpense(id: SemiFixExpenseId) {
        viewModelScope.launch {
            deleteSemiFixExpenseUseCase(id).onFailure {
                showErrorMessage(
                    message = TextWrapper.Resource(R.string.delete_semi_fix_expense_error_message)
                )
            }
        }
    }

    fun onObjectiveMarginChanged(margin: String) {
        _objectiveMarginInput.value = margin
    }

    fun onFillerChanged(filler: String) {
        _fillerInput.value = filler
    }

    fun onPaintChanged(paint: String) {
        _paintInput.value = paint
    }

    fun onVarnishChanged(varnish: String) {
        _varnishInput.value = varnish
    }

    fun onNextPage() {
        when (_currentPage.value) {
            OnboardingPageUi.Staff -> updateScreenPage(OnboardingPageUi.SemiFixExpenses)
            OnboardingPageUi.SemiFixExpenses -> updateScreenPage(OnboardingPageUi.GarageInfo)
            OnboardingPageUi.GarageInfo -> saveGarageInfo()
        }
    }

    fun onPreviousPage() {
        when (_currentPage.value) {
            OnboardingPageUi.SemiFixExpenses -> updateScreenPage(OnboardingPageUi.Staff)
            OnboardingPageUi.GarageInfo -> updateScreenPage(OnboardingPageUi.SemiFixExpenses)
            else -> Unit
        }
    }

    private fun isNextPageEnabled(
        onboardingPage: OnboardingPageUi,
        staffList: List<StaffUi>,
        semiFixExpenses: List<SemiFixExpense>,
        isGarageInformationValid: Boolean,
    ): Boolean = when (onboardingPage) {
        OnboardingPageUi.Staff -> staffList.isNotEmpty()
        OnboardingPageUi.SemiFixExpenses -> semiFixExpenses.isNotEmpty()
        OnboardingPageUi.GarageInfo -> isGarageInformationValid
    }

    private fun updateScreenPage(newContent: OnboardingPageUi) {
        _currentPage.value = newContent
    }

    private fun clearSemiFixExpensesFormInputs() {
        _selectedExpenseOption.value = null
        _expenseAmountInput.value = ""
    }

    private suspend fun saveSemiFixExpense() = saveSemiFixExpenseUseCase(
        SaveSemiFixExpenseInput(
            id = _selectedExpenseOption.value!!.id,
            selectedName = _selectedExpenseOption.value!!.optionName,
            amount = _expenseAmountInput.value
        )
    )

    private fun showSaveSemiFixExpenseError() {
        showErrorMessage(
            message = TextWrapper.Resource(R.string.save_semi_fix_expense_error_message)
        )
        clearAndCloseSemiFixExpenseForm()
    }

    private fun clearAndCloseSemiFixExpenseForm() {
        clearSemiFixExpensesFormInputs()
        closeSemiFixExpenseForm()
    }

    private fun showErrorMessage(message: TextWrapper) {
        _snackbarState.value = SnackbarState(message = message)
    }

    private fun saveGarageInfo() {
        viewModelScope.launch {
            saveGarageInformation()
                .onSuccess { navigator.navigateTo(VehiclesDestination()) }
                .onFailure {
                    Log.d("asdd", it.stackTraceToString())
                    showErrorMessage(
                        message = TextWrapper.Resource(R.string.save_garage_info_error_message),
                    )
                }
        }
    }

    private suspend fun saveGarageInformation(): AppResult<Unit, Throwable> =
        saveGarageInformationUseCase(
            GarageInformationInput(
                objectiveMargin = _objectiveMarginInput.value,
                fillerPrice = _fillerInput.value,
                paintPrice = _paintInput.value,
                varnishPrice = _varnishInput.value,
            )
        ).andThen {
            completeOnboardingUseCase(Unit)
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

private fun SemiFixExpenseOption.toSemiFixExpenseOptionUi() =
    SemiFixExpenseOptionUi(
        id = id.value,
        optionName = name,
    )
