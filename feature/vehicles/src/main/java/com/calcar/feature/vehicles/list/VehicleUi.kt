package com.calcar.feature.vehicles.list

import com.calcar.common.domain.vehicles.entities.Vehicle
import com.calcar.common.domain.vehicles.entities.VehicleStatus

sealed class VehicleUi {
    abstract val id: Long
    abstract val makeModel: String
    abstract val plateNumber: String
    abstract val ownerName: String
    abstract val benefitAmount: Double
    abstract val modificationDate: String

    data class Budgeted(
        override val id: Long,
        override val makeModel: String,
        override val plateNumber: String,
        override val ownerName: String,
        override val benefitAmount: Double,
        override val modificationDate: String,
        val isPending: Boolean,
    ) : VehicleUi()

    data class Other(
        override val id: Long,
        override val makeModel: String,
        override val plateNumber: String,
        override val ownerName: String,
        override val benefitAmount: Double,
        override val modificationDate: String,
        val referenceNumber: String
    ) : VehicleUi()
}

internal fun VehicleTab.toVehicleStatus() = when (this) {
    VehicleTab.Budgeted -> VehicleStatus.Budgeted
    VehicleTab.Repairing -> VehicleStatus.Repairing
    VehicleTab.Finished -> VehicleStatus.Finished
}

internal fun Vehicle.toVehicleUi() = when (this) {
    is Vehicle.Budgeted -> buildBudgetedVehicleUi()
    is Vehicle.Repairing -> buildOtherVehicleUi()
    is Vehicle.Finished -> buildOtherVehicleUi()
}

private fun Vehicle.Budgeted.buildBudgetedVehicleUi() =
    VehicleUi.Budgeted(
        id = id.value,
        makeModel = "$makeModel",
        plateNumber = "$plateNumber",
        ownerName = "${owner.fullName}",
        benefitAmount = 200.0,
        modificationDate = "23/05/2024",
        isPending = isPending,
    )

private fun Vehicle.Repairing.buildOtherVehicleUi() =
    VehicleUi.Other(
        id = id.value,
        makeModel = "$makeModel",
        plateNumber = "$plateNumber",
        ownerName = "${owner.fullName}",
        benefitAmount = 200.0,
        modificationDate = "23/05/2024",
        referenceNumber = referenceNumber,
    )

private fun Vehicle.Finished.buildOtherVehicleUi() =
    VehicleUi.Other(
        id = id.value,
        makeModel = "$makeModel",
        plateNumber = "$plateNumber",
        ownerName = "${owner.fullName}",
        benefitAmount = 200.0,
        modificationDate = "23/05/2024",
        referenceNumber = referenceNumber,
    )