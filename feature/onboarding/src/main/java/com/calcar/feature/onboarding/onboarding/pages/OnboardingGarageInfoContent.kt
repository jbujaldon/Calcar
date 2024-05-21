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
                modifier = Modifier.padding(bottom = 36.dp),
            )
            ProductsSection(
                filler = filler,
                paint = paint,
                varnish = varnish,
                onFillerChanged = onFillerChanged,
                onPaintChanged = onPaintChanged,
                onVarnishChanged = onVarnishChanged,
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

@Composable
private fun ProductsSection(
    filler: String,
    paint: String,
    varnish: String,
    onFillerChanged: (String) -> Unit,
    onPaintChanged: (String) -> Unit,
    onVarnishChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SectionTitle(text = stringResource(R.string.products_title))
        TextFieldInput(
            value = filler,
            onValueChanged = onFillerChanged,
            label = stringResource(R.string.product_filler_placeholder),
            modifier = Modifier.fillMaxWidth()
        )
        TextFieldInput(
            value = paint,
            onValueChanged = onPaintChanged,
            label = stringResource(R.string.product_paint_placeholder),
            modifier = Modifier.fillMaxWidth(),
        )
        TextFieldInput(
            value = varnish,
            onValueChanged = onVarnishChanged,
            label = stringResource(R.string.product_varnish_placeholder),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}