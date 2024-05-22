package com.calcar.common.domain.garage.usecases

import com.calcar.common.core.result.AppResult
import com.calcar.common.core.result.Failure
import com.calcar.common.core.result.Success
import com.calcar.common.core.usecases.UseCaseSuspend
import com.calcar.common.domain.garage.datasource.GarageDataSource
import com.calcar.common.domain.garage.entities.GarageInformation
import com.calcar.common.domain.garage.entities.ObjectiveMargin
import com.calcar.common.domain.garage.entities.ProductPrice

interface SaveGarageInformationUseCase :
    UseCaseSuspend<GarageInformationInput, AppResult<Unit, Throwable>>

data class GarageInformationInput(
    val objectiveMargin: String,
    val fillerPrice: String,
    val paintPrice: String,
    val varnishPrice: String,
)

class SaveGarageInformationUseCaseImpl(
    private val garageDataSource: GarageDataSource,
) : SaveGarageInformationUseCase {

    override suspend fun invoke(params: GarageInformationInput): AppResult<Unit, Throwable> =
        try {
            garageDataSource.saveGarageInformation(params.toGarageInformation())
        } catch (e: Throwable) {
            Failure(e)
        }

    private fun GarageInformationInput.toGarageInformation() =
        GarageInformation(
            objectiveMargin = ObjectiveMargin(objectiveMargin.toDoubleOrNull()),
            fillerPrice = ProductPrice(fillerPrice.toDoubleOrNull()),
            paintPrice = ProductPrice(paintPrice.toDoubleOrNull()),
            varnishPrice = ProductPrice(varnishPrice.toDoubleOrNull()),
        )
}