package com.calcar.common.ui.navigation

sealed interface NavPop {
    data object Nothing : NavPop

    data object Start : NavPop

    data class Inclusive(val upToRoute: String) : NavPop

    data class Exclusive(val upToRoute: String) : NavPop
}