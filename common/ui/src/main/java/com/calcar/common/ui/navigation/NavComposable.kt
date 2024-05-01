package com.calcar.common.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private typealias AnimatedEnterTransition =
        (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)?

private typealias AnimatedExitTransition =
        (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)?

fun NavGraphBuilder.composable(
    destination: Destination,
    enterTransition: AnimatedEnterTransition = null,
    exitTransition: AnimatedExitTransition = null,
    popEnterTransition: AnimatedEnterTransition = null,
    popExitTransition: AnimatedExitTransition = null,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    when (destination) {
        is NoArgDestination -> composable(
            route = destination.route,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            content = content,
        )
        is OptionalArgsDestination -> composable(
            route = destination.getOptionalFullRoute(*destination.paramNames),
            arguments = destination.paramNames.map { param ->
                navArgument(param) { type = NavType.StringType; nullable = true }
            },
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            content = content,
        )
        is ArgsDestination -> composable(
            route = destination.getFullRoute(*destination.paramNames),
            arguments = destination.paramNames.map { param ->
                navArgument(param) { type = NavType.StringType }
            },
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            content = content,
        )
    }
}
