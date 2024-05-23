package com.calcar.feature.garage.navigation

import androidx.navigation.NavGraphBuilder
import com.calcar.common.ui.navigation.NoArgDestination
import com.calcar.common.ui.navigation.composable
import com.calcar.feature.garage.home.GarageScreen

object GarageDestination : NoArgDestination("garage")

fun NavGraphBuilder.garageRoute() {
    composable(GarageDestination) {
        GarageScreen()
    }
}