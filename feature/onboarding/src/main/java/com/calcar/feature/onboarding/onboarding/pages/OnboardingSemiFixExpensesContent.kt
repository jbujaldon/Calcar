package com.calcar.feature.onboarding.onboarding.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.calcar.feature.onboarding.R
import com.calcar.feature.onboarding.ui.components.AddButton
import com.calcar.feature.onboarding.ui.components.EmptyContent
import com.calcar.feature.onboarding.ui.components.PageScaffold

@Composable
internal fun OnboardingSemiFixExpensesContent(
    page: OnboardingPageUi,
    onClickAddSemiFixExpense: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        PageContent(
            page = page,
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
    modifier: Modifier = Modifier,
) {
    PageScaffold(page = page, modifier = modifier) {
        EmptyContent(
            imageRes = painterResource(id = R.drawable.empty_semi_fix_expenses),
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 76.dp)
        )
    }
}