package com.calcar.common.domain.garage.datasource

import com.calcar.common.core.result.AppResult
import com.calcar.common.domain.garage.entities.GarageInformation

interface GarageDataSource {
    suspend fun saveGarageInformation(
        garageInformation: GarageInformation,
    ): AppResult<Unit, Throwable>
}