package com.calcar.feature.vehicles.navigation

import androidx.navigation.NavGraphBuilder
import com.calcar.common.ui.navigation.NoArgDestination
import com.calcar.common.ui.navigation.composable
import com.calcar.feature.vehicles.list.VehiclesScreen

object VehiclesDestination : NoArgDestination("vehicles")

fun NavGraphBuilder.vehiclesRoute() {
    composable(VehiclesDestination) {
        VehiclesScreen()
    }
}
