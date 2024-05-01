package com.calcar.feature.onboarding.welcome

import androidx.lifecycle.ViewModel
import com.calcar.common.ui.navigation.Navigator
import com.calcar.feature.onboarding.navigation.OnboardingDestination

internal class WelcomeViewModel(private val navigator: Navigator) : ViewModel() {
    fun onStart() {
        navigator.navigateTo(OnboardingDestination())
    }
}