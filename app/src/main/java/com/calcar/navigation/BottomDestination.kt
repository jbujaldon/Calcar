package com.calcar.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DriveEta
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.outlined.DriveEta
import androidx.compose.material.icons.outlined.Handyman
import androidx.compose.ui.graphics.vector.ImageVector
import com.calcar.R
import com.calcar.common.ui.navigation.Destination
import com.calcar.feature.garage.navigation.GarageDestination
import com.calcar.feature.vehicles.navigation.VehiclesListDestination

enum class BottomDestination(
    @StringRes val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val destination: Destination,
) {
    Vehicles(
        labelRes = R.string.vehicles_tab,
        selectedIcon = Icons.Filled.DriveEta,
        unselectedIcon = Icons.Outlined.DriveEta,
        destination = VehiclesListDestination
    ),
    Garage(
        labelRes = R.string.garage_tab,
        selectedIcon = Icons.Filled.Handyman,
        unselectedIcon = Icons.Outlined.Handyman,
        destination = GarageDestination
    ),
}