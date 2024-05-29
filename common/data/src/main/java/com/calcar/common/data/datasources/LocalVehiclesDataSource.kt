package com.calcar.common.data.datasources

import com.calcar.common.domain.vehicles.datasources.VehiclesDataSource
import com.calcar.common.domain.vehicles.entities.Vehicle
import com.calcar.common.domain.vehicles.entities.VehicleStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalVehiclesDataSource : VehiclesDataSource {
    override fun getVehiclesWithStatus(status: VehicleStatus): Flow<List<Vehicle>> = flow {
        emit(emptyList())
    }
}