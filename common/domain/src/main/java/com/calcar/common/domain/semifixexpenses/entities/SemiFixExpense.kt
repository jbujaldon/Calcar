package com.calcar.common.domain.semifixexpenses.entities

import com.calcar.common.core.wrappers.TextWrapper

data class SemiFixExpense(
    val id: SemiFixExpenseId,
    val name: TextWrapper,
    val amount: Amount,
)

data class SemiFixExpenseId(val value: Long)

data class Amount private constructor(val value: Double) {
    companion object {
        operator fun invoke(value: Double?): Amount {
            if (value == null) {
                throw IllegalArgumentException("Value must not be null")
            }
            return Amount(value)
        }
    }
}

data class SemiFixExpenseOption(
    val id: SemiFixExpenseId,
    val name: TextWrapper,
)
