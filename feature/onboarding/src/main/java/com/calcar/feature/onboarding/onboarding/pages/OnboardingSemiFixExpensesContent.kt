package com.calcar.feature.onboarding.onboarding.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpense
import com.calcar.common.ui.utils.toEuroCurrency
import com.calcar.feature.onboarding.R
import com.calcar.feature.onboarding.ui.components.AddButton
import com.calcar.feature.onboarding.ui.components.EmptyContent
import com.calcar.feature.onboarding.ui.components.PageScaffold

@Composable
internal fun OnboardingSemiFixExpensesContent(
    page: OnboardingPageUi,
    semiFixExpenses: List<SemiFixExpense>,
    onClickAddSemiFixExpense: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        PageContent(
            page = page,
            semiFixExpenses = semiFixExpenses,
        )
        AddButton(
            text = stringResource(R.string.add_semi_fix_expense_button),
            onClick = onClickAddSemiFixExpense,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        )
    }
}

@Composable
private fun PageContent(
    page: OnboardingPageUi,
    semiFixExpenses: List<SemiFixExpense>,
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
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun SemiFixExpensesList(
    semiFixExpenses: List<SemiFixExpense>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(semiFixExpenses) { semiFixExpense ->
            SemiFixExpenseItem(
                semiFixExpense = semiFixExpense,
                onClickRemove = {},
            )
            HorizontalDivider(modifier = Modifier.padding(start = 24.dp))
        }
    }
}

@Composable
private fun SemiFixExpenseItem(
    semiFixExpense: SemiFixExpense,
    onClickRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val resources = LocalContext.current.resources

    Row(modifier = modifier) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = semiFixExpense.name.getText(resources),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = semiFixExpense.amount.value.toEuroCurrency(),
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        RemoveIcon(
            onClick = onClickRemove,
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
