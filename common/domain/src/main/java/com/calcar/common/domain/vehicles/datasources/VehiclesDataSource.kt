package com.calcar.common.domain.vehicles.datasources

import com.calcar.common.domain.vehicles.entities.Vehicle
import com.calcar.common.domain.vehicles.entities.VehicleStatus
import kotlinx.coroutines.flow.Flow

interface VehiclesDataSource {
    fun getVehiclesWithStatus(status: VehicleStatus): Flow<List<Vehicle>>
}