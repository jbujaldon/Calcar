package com.calcar.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.calcar.feature.onboarding.navigation.WelcomeDestination
import com.calcar.feature.vehicles.navigation.VehiclesDestination
import com.calcar.navigation.CalcarNavHost

@Composable
fun CalcarApp(isOnboardingCompleted: Boolean) {
    val appState = rememberCalcarAppState()
    val startDestination = if (isOnboardingCompleted) {
        VehiclesDestination
    } else {
        WelcomeDestination
    }

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        CalcarNavHost(
            appState = appState,
            modifier = Modifier.padding(innerPadding),
            startDestination = startDestination,
        )
    }
}