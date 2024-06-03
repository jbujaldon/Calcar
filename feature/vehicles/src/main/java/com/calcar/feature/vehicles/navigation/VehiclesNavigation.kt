package com.calcar.feature.vehicles.navigation

import androidx.navigation.NavGraphBuilder
import com.calcar.common.ui.navigation.NoArgDestination
import com.calcar.common.ui.navigation.composable
import com.calcar.feature.vehicles.form.VehicleFormScreen
import com.calcar.feature.vehicles.list.VehiclesScreen

object VehiclesListDestination : NoArgDestination("vehicles")
object VehicleFormDestination : NoArgDestination("vehicle_form")

fun NavGraphBuilder.vehiclesRoute() {
    composable(VehiclesListDestination) {
        VehiclesScreen()
    }
    composable(VehicleFormDestination) {
        VehicleFormScreen()
    }
}
