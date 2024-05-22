package com.calcar.common.domain.user.usecases

import com.calcar.common.core.usecases.UseCase
import com.calcar.common.domain.user.datasources.UserDataSource
import kotlinx.coroutines.flow.Flow

fun interface GetIsOnboardingCompletedUseCase : UseCase<Unit, Flow<Boolean>>

class GetIsOnboardingCompletedUseCaseImpl(
    private val userDataSource: UserDataSource,
) : GetIsOnboardingCompletedUseCase {

    override fun invoke(params: Unit): Flow<Boolean> = userDataSource.isOnboardingCompleted()
}
