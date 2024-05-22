package com.calcar.navigation

import android.annotation.SuppressLint
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import com.calcar.common.ui.navigation.Destination
import com.calcar.common.ui.navigation.NavPop
import com.calcar.common.ui.navigation.Navigator
import com.calcar.common.ui.navigation.Route
import com.calcar.feature.onboarding.navigation.WelcomeDestination
import com.calcar.feature.onboarding.navigation.onboardingRoute
import com.calcar.feature.vehicles.navigation.vehiclesRoute
import com.calcar.ui.CalcarAppState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun CalcarNavHost(
    appState: CalcarAppState,
    modifier: Modifier = Modifier,
    startDestination: Destination = WelcomeDestination,
) {
    val navController = appState.navController
    val navigator: Navigator = koinInject()
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier,
    ) {
        onboardingRoute()
        vehiclesRoute()
    }
    LaunchedEffect(Unit) {
        handleNavEvents(navigator, navController) {
            backDispatcher?.onBackPressed()
        }
    }
}

private fun CoroutineScope.handleNavEvents(
    navigator: Navigator,
    navController: NavHostController,
    onSystemBack: () -> Unit,
) {
    launch {
        navigator.directions.collect { route ->
            navController.currentBackStackEntry?.getParentEntry(navController)
            when (route) {
                is Route.Forward -> navController.navigate(route.route) {
                    setupPopStrategy(route.popStrategy, navController.graph.id)
                    launchSingleTop = route.launchSingleTop
                }
                Route.Up -> navController.navigateUp()
                Route.Back -> onSystemBack()
            }
        }
    }
}

@SuppressLint("RestrictedApi")
fun NavBackStackEntry.getParentEntry(navController: NavController): NavBackStackEntry? =
    destination.parent?.id?.let { parentId ->
        navController.currentBackStack.value.lastOrNull { entry ->
            entry.destination.id == parentId
        }
    }

private fun NavOptionsBuilder.setupPopStrategy(strategy: NavPop, startDestinationId: Int) {
    when (strategy) {
        is NavPop.Exclusive -> popUpTo(strategy.upToRoute) { inclusive = false }
        is NavPop.Inclusive -> popUpTo(strategy.upToRoute) { inclusive = true }
        is NavPop.Nothing -> {}
        is NavPop.Start -> popUpTo(startDestinationId)
    }
}
