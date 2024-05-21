package com.calcar.feature.onboarding.onboarding.pages

import androidx.annotation.StringRes
import com.calcar.feature.onboarding.R

enum class OnboardingPageUi(
    @StringRes val title: Int,
    @StringRes val description: Int,
) {
    Staff(
        title = R.string.onboarding_page_staff_title,
        description = R.string.onboarding_page_staff_description,
    ),
    SemiFixExpenses(
        title = R.string.onboarding_page_semi_fix_expenses_title,
        description = R.string.onboarding_page_semi_fix_expenses_description,
    ),
    GarageInfo(
        title = R.string.onboarding_page_garage_info_title,
        description = R.string.onboarding_page_garage_info_description,
    )
}
