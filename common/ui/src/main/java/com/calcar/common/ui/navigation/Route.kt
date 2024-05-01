package com.calcar.common.ui.navigation

sealed class Route {
    open class Forward(
        val route: String,
        val popStrategy: NavPop = NavPop.Nothing,
        val launchSingleTop: Boolean = false,
    ) : Route()

    data object Back : Route()

    data object Up : Route()
}