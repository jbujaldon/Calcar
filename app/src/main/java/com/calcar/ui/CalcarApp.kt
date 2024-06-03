package com.calcar.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.calcar.feature.onboarding.navigation.WelcomeDestination
import com.calcar.feature.vehicles.navigation.VehiclesListDestination
import com.calcar.navigation.BottomDestination
import com.calcar.navigation.CalcarNavHost

@Composable
fun CalcarApp(isOnboardingCompleted: Boolean) {
    val appState = rememberCalcarAppState()
    val startDestination = if (isOnboardingCompleted) VehiclesListDestination else WelcomeDestination

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                BottomNavigationBar(
                    currentDestination = appState.currentBottomDestination,
                    destinations = BottomDestination.entries,
                    onClickDestination = appState::navigateToBottomDestination
                )
            }
        }
    ) { innerPadding ->
        CalcarNavHost(
            appState = appState,
            modifier = Modifier.padding(innerPadding),
            startDestination = startDestination,
        )
    }
}

@Composable
private fun BottomNavigationBar(
    currentDestination: BottomDestination?,
    destinations: List<BottomDestination>,
    onClickDestination: (BottomDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        destinations.forEach { destination ->
            val isSelected = destination == currentDestination
            val icon = if (isSelected) destination.selectedIcon else destination.unselectedIcon
            NavigationBarItem(
                selected = isSelected,
                onClick = { onClickDestination(destination) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                    )
                },
                label = { Text(text = stringResource(destination.labelRes)) },
            )
        }
    }
}