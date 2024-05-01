package com.calcar.common.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class Navigator {
    private val _directions = MutableSharedFlow<Route>(extraBufferCapacity = 1)
    val directions: SharedFlow<Route> = _directions

    fun navigateTo(
        route: String,
        popStrategy: NavPop = NavPop.Nothing,
        launchSingleTop: Boolean = false,
    ) {
        _directions.tryEmit(Route.Forward(route, popStrategy, launchSingleTop))
    }

    /**
     * Navigation to be performed when the user presses the system back button.
     * **Not for in-app back arrow buttons**.
     */
    fun systemBack() {
        _directions.tryEmit(Route.Back)
    }

    /**
     * Navigation to be performed when the user presses the in-app back button.
     * The behavior is not always the same as pressing the system back button.
     */
    fun navigateUp() {
        _directions.tryEmit(Route.Up)
    }
}