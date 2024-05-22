package com.calcar.common.domain.user.usecases

import com.calcar.common.core.result.AppResult
import com.calcar.common.core.usecases.UseCaseSuspend
import com.calcar.common.domain.user.datasources.UserDataSource

fun interface CompleteOnboardingUseCase : UseCaseSuspend<Unit, AppResult<Unit, Throwable>>

class CompleteOnboardingUseCaseImpl(
    private val userDataSource: UserDataSource,
) : CompleteOnboardingUseCase {

    override suspend fun invoke(params: Unit): AppResult<Unit, Throwable> =
        userDataSource.completeOnboarding(true)
}