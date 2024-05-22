package com.calcar.common.domain.garage.entities

data class GarageInformation(
    val objectiveMargin: ObjectiveMargin,
    val fillerPrice: ProductPrice,
    val paintPrice: ProductPrice,
    val varnishPrice: ProductPrice,
)

data class ObjectiveMargin private constructor(val value: Double) {
    companion object {
        operator fun invoke(value: Double?): ObjectiveMargin {
            if (value == null || value < 0) {
                throw IllegalArgumentException("Value cannot be null or negative")
            }
            return ObjectiveMargin(value)
        }
    }
}

data class ProductPrice private constructor(val value: Double) {
    companion object {
        operator fun invoke(value: Double?): ProductPrice {
            if (value == null || value < 0) {
                throw IllegalArgumentException("Value cannot be null or negative")
            }
            return ProductPrice(value)
        }
    }
}
