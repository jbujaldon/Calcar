package com.calcar.feature.onboarding.onboarding.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpense
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpenseId
import com.calcar.common.ui.models.SemiFixExpenseOptionUi
import com.calcar.common.ui.utils.toEuroCurrency
import com.calcar.feature.onboarding.R
import com.calcar.feature.onboarding.ui.components.AddButton
import com.calcar.feature.onboarding.ui.components.EmptyContent
import com.calcar.feature.onboarding.ui.components.PageScaffold
import com.calcar.feature.onboarding.ui.components.TextFieldInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OnboardingSemiFixExpensesContent(
    page: OnboardingPageUi,
    semiFixExpenses: List<SemiFixExpense>,
    onClickAddSemiFixExpense: () -> Unit,
    showAddSemiFixExpenseForm: Boolean,
    selectedSemiFixExpenseOption: SemiFixExpenseOptionUi?,
    semiFixExpenseOptions: List<SemiFixExpenseOptionUi>,
    semiFixExpenseAmountInput: String,
    onSelectSemiFixExpenseOption: (SemiFixExpenseOptionUi) -> Unit,
    onSemiFixExpenseAmountChanged: (String) -> Unit,
    onSaveSemiFixExpense: () -> Unit,
    onSaveAndAddOtherSemiFixExpense: () -> Unit,
    enableSaveSemiFixExpense: Boolean,
    onClickCloseSemiFixExpenseForm: () -> Unit,
    onDeleteSemiFixExpense: (SemiFixExpenseId) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    
    Box(modifier = modifier.fillMaxSize()) {
        PageContent(
            page = page,
            semiFixExpenses = semiFixExpenses,
            onDeleteSemiFixExpense = onDeleteSemiFixExpense,
        )
        AddButton(
            text = stringResource(R.string.add_semi_fix_expense_button),
            onClick = onClickAddSemiFixExpense,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        )
        if (showAddSemiFixExpenseForm) {
            ModalBottomSheet(
                onDismissRequest = onClickCloseSemiFixExpenseForm,
                sheetState = sheetState,
                modifier = Modifier.navigationBarsPadding()
            ) {
                AddSemiFixExpenseForm(
                    selectedSemiFixExpenseOption = selectedSemiFixExpenseOption,
                    semiFixExpenseAmountInput = semiFixExpenseAmountInput,
                    semiFixExpenseOptions = semiFixExpenseOptions,
                    onSelectSemiFixExpenseOption = onSelectSemiFixExpenseOption,
                    onSemiFixExpenseAmountChanged = onSemiFixExpenseAmountChanged,
                    onSaveSemiFixExpense = onSaveSemiFixExpense,
                    onSaveAndAddOtherSemiFixExpense = onSaveAndAddOtherSemiFixExpense,
                    enableSaveSemiFixExpense = enableSaveSemiFixExpense,
                    coroutineScope = coroutineScope,
                    sheetState = sheetState,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 16.dp),
                )
            }
        }
    }
}

@Composable
private fun PageContent(
    page: OnboardingPageUi,
    semiFixExpenses: List<SemiFixExpense>,
    onDeleteSemiFixExpense: (SemiFixExpenseId) -> Unit,
    modifier: Modifier = Modifier,
) {
    PageScaffold(page = page, modifier = modifier) {
        if (semiFixExpenses.isEmpty()) {
            EmptyContent(
                imageRes = painterResource(id = R.drawable.empty_semi_fix_expenses),
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 76.dp)
            )
        } else {
            SemiFixExpensesList(
                semiFixExpenses = semiFixExpenses,
                onDelete = onDeleteSemiFixExpense,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun SemiFixExpensesList(
    semiFixExpenses: List<SemiFixExpense>,
    onDelete: (SemiFixExpenseId) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(semiFixExpenses) { semiFixExpense ->
            SemiFixExpenseItem(
                semiFixExpense = semiFixExpense,
                onClickDelete = { onDelete(semiFixExpense.id) },
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = 24.dp)
            )
        }
    }
}

@Composable
private fun SemiFixExpenseItem(
    semiFixExpense: SemiFixExpense,
    onClickDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val resources = LocalContext.current.resources

    Row(
        modifier = modifier
            .padding(start = 24.dp, end = 8.dp)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = semiFixExpense.name.getText(resources),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = semiFixExpense.amount.value.toEuroCurrency(),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        RemoveIcon(
            onClick = onClickDelete,
            modifier = Modifier.align(Alignment.CenterVertically),
        )
    }
}

@Composable
private fun RemoveIcon(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddSemiFixExpenseForm(
    selectedSemiFixExpenseOption: SemiFixExpenseOptionUi?,
    semiFixExpenseOptions: List<SemiFixExpenseOptionUi>,
    semiFixExpenseAmountInput: String,
    onSelectSemiFixExpenseOption: (SemiFixExpenseOptionUi) -> Unit,
    onSemiFixExpenseAmountChanged: (String) -> Unit,
    onSaveSemiFixExpense: () -> Unit,
    onSaveAndAddOtherSemiFixExpense: () -> Unit,
    enableSaveSemiFixExpense: Boolean,
    coroutineScope: CoroutineScope,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        FormTitle()
        AddSemiFixExpenseInputs(
            selectedOptionUi = selectedSemiFixExpenseOption,
            semiFixExpenseAmount = semiFixExpenseAmountInput,
            semiFixExpenseOptions = semiFixExpenseOptions,
            onSelectSemiFixExpenseOption = onSelectSemiFixExpenseOption,
            onSemiFixExpenseAmountChanged = onSemiFixExpenseAmountChanged,
            modifier = Modifier.padding(top = 32.dp, bottom = 42.dp)
        )
        SaveSemiFixExpenseButtons(
            onClickSave = {
                saveAddSemiFixExpense(coroutineScope, sheetState, onSaveSemiFixExpense)
            },
            onClickSaveAndAddOther = onSaveAndAddOtherSemiFixExpense,
            enableSave = enableSaveSemiFixExpense,
        )
    }
}

@Composable
private fun FormTitle(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.add_semi_fix_expense_form_title),
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall,
    )
}

@Composable
private fun AddSemiFixExpenseInputs(
    selectedOptionUi: SemiFixExpenseOptionUi?,
    semiFixExpenseOptions: List<SemiFixExpenseOptionUi>,
    semiFixExpenseAmount: String,
    onSelectSemiFixExpenseOption: (SemiFixExpenseOptionUi) -> Unit,
    onSemiFixExpenseAmountChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        SemiFixExpenseOptionSelector(
            selectedOption = selectedOptionUi,
            options = semiFixExpenseOptions,
            onSelectOption = onSelectSemiFixExpenseOption,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        TextFieldInput(
            value = semiFixExpenseAmount,
            onValueChanged = onSemiFixExpenseAmountChanged,
            label = stringResource(R.string.semi_fix_expense_amount_placeholder),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SemiFixExpenseOptionSelector(
    selectedOption: SemiFixExpenseOptionUi?,
    options: List<SemiFixExpenseOptionUi>,
    onSelectOption: (SemiFixExpenseOptionUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    val resources = LocalContext.current.resources
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
    ) {
        TextFieldInput(
            value = selectedOption?.optionName?.getText(resources) ?: "",
            onValueChanged = {},
            readOnly = true,
            label = stringResource(R.string.select_semi_fix_expense_name_placeholder),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(isExpanded) },
            modifier = modifier
                .menuAnchor()
                .fillMaxWidth(),
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(text = it.optionName.getText(resources)) },
                    onClick = {
                        onSelectOption(it)
                        isExpanded = false
                    },
                )
            }
        }
    }
}

@Composable
private fun SaveSemiFixExpenseButtons(
    onClickSave: () -> Unit,
    onClickSaveAndAddOther: () -> Unit,
    enableSave: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Button(
            onClick = onClickSave,
            modifier = Modifier.fillMaxWidth(),
            enabled = enableSave,
        ) {
            Text(text = stringResource(R.string.save_semi_fix_expense_button))
        }
        OutlinedButton(
            onClick = onClickSaveAndAddOther,
            modifier = Modifier.fillMaxWidth(),
            enabled = enableSave,
        ) {
            Text(text = stringResource(R.string.save_and_add_other_semi_fix_expense_button))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun saveAddSemiFixExpense(
    coroutineScope: CoroutineScope,
    sheetState: SheetState,
    onClose: () -> Unit,
) {
    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
        if (!sheetState.isVisible) {
            onClose()
        }
    }
}
