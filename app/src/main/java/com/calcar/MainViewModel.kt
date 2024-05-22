package com.calcar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calcar.common.domain.user.usecases.GetIsOnboardingCompletedUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    getIsOnboardingCompletedUseCase: GetIsOnboardingCompletedUseCase
) : ViewModel() {

    val initialLoadState: StateFlow<InitialLoadState> = getIsOnboardingCompletedUseCase(Unit)
        .map { InitialLoadState.Success(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), InitialLoadState.Loading)
}

sealed interface InitialLoadState {
    data object Loading : InitialLoadState
    data class Success(val isOnboardingCompleted: Boolean? = null) : InitialLoadState
}