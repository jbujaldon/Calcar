package com.calcar.common.data.datasources

import androidx.datastore.core.DataStore
import com.calcar.common.core.result.AppResult
import com.calcar.common.core.result.appRunCatching
import com.calcar.common.datastore.GarageInformationPreferences
import com.calcar.common.datastore.copy
import com.calcar.common.domain.garage.datasource.GarageDataSource
import com.calcar.common.domain.garage.entities.GarageInformation

class LocalGarageDataSource(
    private val garageDataStore: DataStore<GarageInformationPreferences>,
) : GarageDataSource {

    override suspend fun saveGarageInformation(
        garageInformation: GarageInformation,
    ): AppResult<Unit, Throwable> = appRunCatching {
        garageDataStore.updateData {
            it.copy {
                this.objectiveMargin = garageInformation.objectiveMargin.value
                this.fillerPrice = garageInformation.fillerPrice.value
                this.paintPrice = garageInformation.paintPrice.value
                this.varnishPrice = garageInformation.varnishPrice.value
            }
        }
    }
}