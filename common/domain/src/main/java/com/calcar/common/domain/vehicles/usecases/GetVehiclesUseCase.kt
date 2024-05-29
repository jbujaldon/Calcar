package com.calcar.common.domain.vehicles.usecases

import com.calcar.common.core.usecases.UseCase
import com.calcar.common.domain.vehicles.datasources.VehiclesDataSource
import com.calcar.common.domain.vehicles.entities.Vehicle
import com.calcar.common.domain.vehicles.entities.VehicleStatus
import kotlinx.coroutines.flow.Flow

fun interface GetVehiclesUseCase : UseCase<VehicleStatus, Flow<List<Vehicle>>>

class GetVehiclesUseCaseImpl(
    private val vehiclesDataSource: VehiclesDataSource,
) : GetVehiclesUseCase {

    override fun invoke(params: VehicleStatus): Flow<List<Vehicle>> =
        vehiclesDataSource.getVehiclesWithStatus(params)
}