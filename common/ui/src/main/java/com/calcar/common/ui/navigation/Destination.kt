package com.calcar.common.ui.navigation

sealed class Destination(open val route: String) {
}

open class NoArgDestination(override val route: String) : Destination(route) {
    operator fun invoke(): String = route
}

open class OptionalArgsDestination(
    override val route: String,
    vararg val paramNames: String,
) : Destination(route) {

    operator fun invoke(vararg params: String?): String =
        params.foldIndexed(getOptionalFullRoute(*paramNames)) { index, route, paramValue ->
            val paramName = paramNames[index]
            route.replace("={$paramName}", "=$paramValue")
        }

    fun getOptionalFullRoute(vararg params: String?) =
        if (params.isNotEmpty()) buildRoute(*params) else route

    private fun buildRoute(vararg params: String?): String =
        params.foldIndexed(route) { index, acc, param ->
            if (index == 0) {
                "$acc?$param={$param}"
            } else {
                "$acc&$param={$param}"
            }
        }
}

open class ArgsDestination(
    override val route: String,
    vararg val paramNames: String,
) : Destination(route) {

    operator fun invoke(vararg params: String?): String =
        params.foldIndexed(getFullRoute(*paramNames)) { index, route, paramValue ->
            val paramName = paramNames[index]
            route.replace("/{$paramName}", "/$paramValue")
        }

    fun getFullRoute(vararg params: String) =
        if (params.isNotEmpty()) buildRoute(*params) else route

    private fun buildRoute(vararg params: String): String =
        params.fold(route) { acc, param -> "$acc/{$param}" }
}
