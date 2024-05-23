package com.calcar.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.calcar.feature.garage.navigation.GarageDestination
import com.calcar.feature.vehicles.navigation.VehiclesDestination
import com.calcar.navigation.BottomDestination

@Composable
fun rememberCalcarAppState(
    navController: NavHostController = rememberNavController(),
): CalcarAppState = remember(navController) {
    CalcarAppState(navController)
}

class CalcarAppState(val navController: NavHostController) {

    private val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val currentBottomDestination: BottomDestination?
        @Composable
        get() = when (currentDestination?.route) {
            VehiclesDestination() -> BottomDestination.Vehicles
            GarageDestination() -> BottomDestination.Garage
            else -> null
        }

    val shouldShowBottomBar: Boolean
        @Composable
        get() = currentDestination?.route in BottomDestination.entries.map { it.destination.route }

    fun navigateToBottomDestination(bottomDestination: BottomDestination) {
        val destinationNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (bottomDestination) {
            BottomDestination.Vehicles -> navController.navigate(
                route = VehiclesDestination(),
                navOptions = destinationNavOptions,
            )
            BottomDestination.Garage -> navController.navigate(
                route = GarageDestination(),
                navOptions = destinationNavOptions,
            )
        }
    }
}