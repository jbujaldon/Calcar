package com.calcar.common.domain.semifixexpenses.usecases

import com.calcar.common.core.result.AppResult
import com.calcar.common.core.usecases.UseCaseSuspend
import com.calcar.common.core.wrappers.TextWrapper
import com.calcar.common.domain.semifixexpenses.datasource.SemiFixExpensesDataSource
import com.calcar.common.domain.semifixexpenses.entities.Amount
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpense
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpenseId

fun interface SaveSemiFixExpenseUseCase :
    UseCaseSuspend<SaveSemiFixExpenseInput, AppResult<Unit, Throwable>>

data class SaveSemiFixExpenseInput(
    val id: Long,
    val selectedName: TextWrapper,
    val amount: String,
)

class SaveSemiFixExpenseUseCaseImpl(
    private val semiFixExpensesDataSource: SemiFixExpensesDataSource,
) : SaveSemiFixExpenseUseCase {

    override suspend fun invoke(params: SaveSemiFixExpenseInput): AppResult<Unit, Throwable> =
        semiFixExpensesDataSource.saveNewSemiFixExpense(params.toDomainSemiFixExpense())

    private fun SaveSemiFixExpenseInput.toDomainSemiFixExpense() =
        SemiFixExpense(
            id = SemiFixExpenseId(id),
            name = selectedName,
            amount = Amount(amount.toDoubleOrNull()),
        )
}