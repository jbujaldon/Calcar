package com.calcar.common.data.datasources

import androidx.datastore.core.DataStore
import com.calcar.common.core.result.AppResult
import com.calcar.common.core.result.appRunCatching
import com.calcar.common.datastore.UserPreferences
import com.calcar.common.datastore.copy
import com.calcar.common.domain.user.datasources.UserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserDataSource(
    private val userDataStore: DataStore<UserPreferences>
) : UserDataSource {

    override fun isOnboardingCompleted(): Flow<Boolean> = userDataStore.data.map {
        it.isOnboardingCompleted
    }

    override suspend fun completeOnboarding(
        isCompleted: Boolean,
    ): AppResult<Unit, Throwable> = appRunCatching {
        userDataStore.updateData {
            it.copy { this.isOnboardingCompleted = isCompleted }
        }
    }
}