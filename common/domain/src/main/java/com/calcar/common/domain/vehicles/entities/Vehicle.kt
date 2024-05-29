package com.calcar.common.domain.vehicles.entities

import com.calcar.common.domain.entities.FullName

sealed class Vehicle {
    abstract val id: VehicleId
    abstract val makeModel: MakeModel
    abstract val plateNumber: PlateNumber
    abstract val owner: Owner

    data class Budgeted(
        override val id: VehicleId,
        override val makeModel: MakeModel,
        override val plateNumber: PlateNumber,
        override val owner: Owner,
        val isPending: Boolean,
    ) : Vehicle()

    data class Repairing(
        override val id: VehicleId,
        override val makeModel: MakeModel,
        override val plateNumber: PlateNumber,
        override val owner: Owner,
        val referenceNumber: String
    ) : Vehicle()

    data class Finished(
        override val id: VehicleId,
        override val makeModel: MakeModel,
        override val plateNumber: PlateNumber,
        override val owner: Owner,
        val referenceNumber: String,
    ) : Vehicle()
}

enum class VehicleStatus {
    Budgeted,
    Repairing,
    Finished,
}

data class VehicleId(val value: Long)

data class MakeModel(val make: String, val model: String, val year: Int) {
    init {
        require(make.isNotEmpty() && model.isNotEmpty() && year > 0) {
            "Make, model, and year must be provided"
        }
    }

    override fun toString(): String = "$make $model $year"
}

data class PlateNumber(val value: String) {
    init {
        require(value.isNotEmpty()) {
            "Plate number must be provided"
        }
    }

    override fun toString(): String = value
}

data class Owner(val fullName: FullName)