package com.calcar.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import com.calcar.common.ui.navigation.NoArgDestination
import com.calcar.common.ui.navigation.composable
import com.calcar.feature.onboarding.onboarding.pages.OnboardingScreen
import com.calcar.feature.onboarding.onboarding.staff.AddStaffScreen
import com.calcar.feature.onboarding.welcome.WelcomeScreen

object WelcomeDestination : NoArgDestination("welcome")
object OnboardingDestination : NoArgDestination("onboarding")
object OnboardingAddStaffDestination : NoArgDestination("onboarding_add_staff")

fun NavGraphBuilder.onboardingRoute() {
    composable(WelcomeDestination) {
        WelcomeScreen()
    }
    composable(OnboardingDestination) {
        OnboardingScreen()
    }
    composable(OnboardingAddStaffDestination) {
        AddStaffScreen()
    }
}