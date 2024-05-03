package com.calcar.common.domain.semifixexpenses.entities

import com.calcar.common.core.wrappers.TextWrapper

data class SemiFixExpense(
    val id: SemiFixExpenseId,
    val name: TextWrapper,
    val amount: Amount,
)

data class SemiFixExpenseId(val value: Long)

data class Amount(val value: Double) {
    init {
        require(value > 0.0) { "Amount must be positive" }
    }
}

data class SemiFixExpenseOption(
    val id: SemiFixExpenseId,
    val name: TextWrapper,
)
