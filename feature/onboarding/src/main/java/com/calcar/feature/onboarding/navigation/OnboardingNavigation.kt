package com.calcar.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import com.calcar.common.ui.navigation.NoArgDestination
import com.calcar.common.ui.navigation.composable
import com.calcar.feature.onboarding.onboarding.pages.OnboardingScreen
import com.calcar.feature.onboarding.onboarding.staff.AddStaffScreen
import com.calcar.feature.onboarding.welcome.WelcomeScreen

object WelcomeDestination : NoArgDestination("welcome_route")
object OnboardingDestination : NoArgDestination("onboarding_route")
object OnboardingAddStaffDestination : NoArgDestination("onboarding_add_staff_route")

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