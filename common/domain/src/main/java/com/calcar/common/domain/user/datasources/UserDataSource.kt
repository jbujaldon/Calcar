package com.calcar.common.domain.user.datasources

import com.calcar.common.core.result.AppResult
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun isOnboardingCompleted(): Flow<Boolean>
    suspend fun completeOnboarding(isCompleted: Boolean): AppResult<Unit, Throwable>
}