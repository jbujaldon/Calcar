package com.calcar.feature.onboarding.onboarding.staff

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calcar.feature.onboarding.R
import com.calcar.common.ui.models.ProfessionUi
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AddStaffScreen(viewModel: AddStaffViewModel = koinViewModel()) {
    val step by viewModel.step.collectAsStateWithLifecycle()
    val selectedProfession by viewModel.selectedProfession.collectAsStateWithLifecycle()
    val name by viewModel.name.collectAsStateWithLifecycle()
    val lastName by viewModel.lastName.collectAsStateWithLifecycle()
    val salary by viewModel.salary.collectAsStateWithLifecycle()
    val agreementHours by viewModel.agreementHours.collectAsStateWithLifecycle()

    AddStaffContent(
        step = step,
        selectedProfession = selectedProfession,
        onSelectProfession = viewModel::onSelectProfession,
        onClose = viewModel::onCloseForm,
        onContinue = viewModel::onContinueToForm,
        onBackToSelection = viewModel::onBackToSelection,
        name = name,
        lastName = lastName,
        salary = salary,
        agreementHours = agreementHours,
        onNameChanged = viewModel::onNameChanged,
        onLastNameChanged = viewModel::onLastNameChanged,
        onSalaryChanged = viewModel::onSalaryChanged,
        onAgreementHoursChanged = viewModel::onAgreementHoursChanged,
        onSave = viewModel::onSave,
        onSaveAndAddOther = viewModel::onSaveAndAddOther,
    )
}

@Composable
private fun AddStaffContent(
    step: AddStaffFormStep,
    selectedProfession: ProfessionUi,
    onSelectProfession: (ProfessionUi) -> Unit,
    onClose: () -> Unit,
    onContinue: () -> Unit,
    onBackToSelection: () -> Unit,
    name: String,
    lastName: String,
    salary: String,
    agreementHours: String,
    onNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onSalaryChanged: (String) -> Unit,
    onAgreementHoursChanged: (String) -> Unit,
    onSave: () -> Unit,
    onSaveAndAddOther: () -> Unit,
) {
    if (step == AddStaffFormStep.Selection) {
        ProfessionSelectionContent(
            selectedProfession = selectedProfession,
            onSelectProfession = onSelectProfession,
            onClose = onClose,
            onContinue = onContinue,
        )
    } else {
        FormContent(
            profession = stringResource(selectedProfession.label).lowercase(),
            onBackToSelection = onBackToSelection,
            name = name,
            lastName = lastName,
            salary = salary,
            agreementHours = agreementHours,
            onNameChanged = onNameChanged,
            onLastNameChanged = onLastNameChanged,
            onSalaryChanged = onSalaryChanged,
            onAgreementHoursChanged = onAgreementHoursChanged,
            isStaffDirect = !selectedProfession.isIndirect(),
            onSave = onSave,
            onSaveAndAddOther = onSaveAndAddOther,
        )
    }
}

@Composable
private fun ProfessionSelectionContent(
    selectedProfession: ProfessionUi,
    onSelectProfession: (ProfessionUi) -> Unit,
    onClose: () -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        ProfessionSelectionHeader(onClickClose = onClose)
        WorkProfessionList(
            selectedProfession = selectedProfession,
            onSelectProfession = onSelectProfession,
            modifier = Modifier.padding(horizontal = 24.dp),
        )
        PrimaryActionButton(
            text = stringResource(R.string.staff_continue_button),
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 48.dp),
        )
    }
}

@Composable
private fun FormContent(
    profession: String,
    onBackToSelection: () -> Unit,
    name: String,
    lastName: String,
    salary: String,
    agreementHours: String,
    onNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onSalaryChanged: (String) -> Unit,
    onAgreementHoursChanged: (String) -> Unit,
    isStaffDirect: Boolean,
    onSave: () -> Unit,
    onSaveAndAddOther: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        FormHeader(
            onClickBack = onBackToSelection,
            profession = profession,
        )
        FormInputsContent(
            name = name,
            lastName = lastName,
            salary = salary,
            agreementHours = agreementHours,
            onNameChanged = onNameChanged,
            onLastNameChanged = onLastNameChanged,
            onSalaryChanged = onSalaryChanged,
            onAgreementHoursChanged = onAgreementHoursChanged,
            isStaffDirect = isStaffDirect,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        FormButtons(
            onClickSave = onSave,
            onClickSaveAndAddOther = onSaveAndAddOther,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 48.dp),
        )
    }
}

@Composable
private fun FormInputsContent(
    name: String,
    lastName: String,
    salary: String,
    agreementHours: String,
    onNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onSalaryChanged: (String) -> Unit,
    onAgreementHoursChanged: (String) -> Unit,
    isStaffDirect: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        PersonalInformationInputs(
            name = name,
            lastName = lastName,
            onNameChanged = onNameChanged,
            onLastNameChanged = onLastNameChanged,
        )
        WorkInformationInputs(
            isStaffDirect = isStaffDirect,
            salary = salary,
            agreementHours = agreementHours,
            onSalaryChanged = onSalaryChanged,
            onAgreementHoursChanged = onAgreementHoursChanged,
            modifier = Modifier.padding(top = 32.dp),
        )
    }
}

@Composable
private fun PersonalInformationInputs(
    name: String,
    lastName: String,
    onNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionTitle(text = stringResource(R.string.staff_personal_information_title))
        TextFieldInput(
            value = name,
            onValueChanged = onNameChanged,
            label = stringResource(R.string.staff_name_placeholder),
            modifier = Modifier.fillMaxWidth(),
        )
        TextFieldInput(
            value = lastName,
            onValueChanged = onLastNameChanged,
            label = stringResource(R.string.staff_last_names_placeholder),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun WorkInformationInputs(
    isStaffDirect: Boolean,
    salary: String,
    agreementHours: String,
    onSalaryChanged: (String) -> Unit,
    onAgreementHoursChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionTitle(text = stringResource(R.string.staff_work_information_title))
        TextFieldInput(
            value = salary,
            onValueChanged = onSalaryChanged,
            label = stringResource(R.string.staff_salary_placeholder),
            modifier = Modifier.fillMaxWidth(),
        )
        if (isStaffDirect) {
            TextFieldInput(
                value = agreementHours,
                onValueChanged = onAgreementHoursChanged,
                label = stringResource(R.string.staff_agreement_hours_placeholder),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun ProfessionSelectionHeader(onClickClose: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        CloseButton(onClick = onClickClose)
        Title(
            text = stringResource(R.string.staff_profession_selection_title),
            modifier = Modifier.padding(top = 20.dp, start = 8.dp, bottom = 32.dp),
        )
    }
}

@Composable
private fun FormHeader(
    onClickBack: () -> Unit,
    profession: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(16.dp)) {
        BackButton(onClick = onClickBack)
        Title(
            text = stringResource(R.string.staff_form_title, profession),
            modifier = Modifier.padding(top = 20.dp, start = 8.dp, bottom = 32.dp),
        )
    }
}

@Composable
private fun CloseButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
        )
    }
}

@Composable
private fun BackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
        )
    }
}

@Composable
private fun Title(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.headlineLarge,
    )
}

@Composable
private fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall,
        )
        HorizontalDivider(modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
private fun WorkProfessionList(
    selectedProfession: ProfessionUi,
    onSelectProfession: (ProfessionUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ProfessionUi.entries.forEach { profession ->
            SelectableOption(
                text = stringResource(profession.label),
                isSelected = profession == selectedProfession,
                onClick = { onSelectProfession(profession) }
            )
        }
    }
}

@Composable
private fun SelectableOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }
    val borderColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outlineVariant
    }
    val icon = if (isSelected) {
        Icons.Default.CheckCircleOutline
    } else {
        Icons.Default.RadioButtonUnchecked
    }

    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun TextFieldInput(
    value: String,
    onValueChanged: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier,
        label = { Text(text = label) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            errorContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        )
    )
}

@Composable
private fun FormButtons(
    onClickSave: () -> Unit,
    onClickSaveAndAddOther: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        PrimaryActionButton(
            text = stringResource(R.string.staff_save_button),
            onClick = onClickSave,
            modifier = Modifier.fillMaxWidth(),
        )
        SecondaryActionButton(
            text = stringResource(R.string.staff_save_and_add_other_button),
            onClick = onClickSaveAndAddOther,
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
        )
    }
}

@Composable
private fun PrimaryActionButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(text = text)
    }
}

@Composable
private fun SecondaryActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(text = text)
    }
}
