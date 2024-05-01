package com.calcar.common.core.usecases

fun interface UseCase<Params, Return> {
    operator fun invoke(params: Params): Return
}

fun interface UseCaseSuspend<Params, Return> {
    suspend operator fun invoke(params: Params): Return
}
