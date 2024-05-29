package com.calcar.feature.vehicles.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calcar.common.domain.vehicles.entities.Vehicle
import com.calcar.common.domain.vehicles.entities.VehicleId
import com.calcar.common.domain.vehicles.entities.VehicleStatus
import com.calcar.common.domain.vehicles.usecases.GetVehiclesUseCase
import com.calcar.common.ui.models.Tab
import com.calcar.common.ui.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class VehiclesViewModel(
    private val navigator: Navigator,
    getVehiclesUseCase: GetVehiclesUseCase,
) : ViewModel() {

    private val _currentTab = MutableStateFlow(VehicleTab.Budgeted)
    val currentTab: StateFlow<VehicleTab> = _currentTab.asStateFlow()

    val vehicles: StateFlow<List<VehicleUi>> = _currentTab.flatMapLatest { tab ->
        getVehiclesUseCase(tab.toVehicleStatus()).map { vehicles ->
            vehicles.map(Vehicle::toVehicleUi)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), emptyList())

    fun onTabChanged(tab: Tab) {
        _currentTab.value = tab as VehicleTab
    }

    fun onAddVehicle() {
        navigator.navigateTo("")
    }

    fun onSelectVehicle() {
        navigator.navigateTo("")
    }
}
