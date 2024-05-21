package com.calcar.feature.onboarding.onboarding.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.calcar.feature.onboarding.R
import com.calcar.feature.onboarding.ui.components.PageScaffold
import com.calcar.feature.onboarding.ui.components.SectionTitle
import com.calcar.feature.onboarding.ui.components.TextFieldInput

@Composable
internal fun OnboardingGarageInfoContent(
    page: OnboardingPageUi,
    objectiveMargin: String,
    filler: String,
    paint: String,
    varnish: String,
    onObjectiveMarginChanged: (String) -> Unit,
    onFillerChanged: (String) -> Unit,
    onPaintChanged: (String) -> Unit,
    onVarnishChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    PageScaffold(page = page, modifier = modifier) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            ObjectiveMarginSection(
                objectiveMargin = objectiveMargin,
                onObjectiveMarginChanged = onObjectiveMarginChanged,
            )
        }
    }
}

@Composable
private fun ObjectiveMarginSection(
    objectiveMargin: String,
    onObjectiveMarginChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SectionTitle(text = stringResource(R.string.objective_margin_title))
        TextFieldInput(
            value = objectiveMargin,
            onValueChanged = onObjectiveMarginChanged,
            label = stringResource(R.string.objective_margin_placeholder),
            modifier = Modifier.fillMaxWidth()
        )
    }
}