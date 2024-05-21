package com.calcar.common.domain.semifixexpenses.usecases

import com.calcar.common.core.result.AppResult
import com.calcar.common.core.usecases.UseCaseSuspend
import com.calcar.common.domain.semifixexpenses.datasource.SemiFixExpensesDataSource
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpense
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpenseId

fun interface DeleteSemiFixExpenseUseCase : UseCaseSuspend<SemiFixExpenseId, AppResult<Unit, Throwable>>

class DeleteSemiFixExpenseUseCaseImpl(
    private val semiFixExpensesDataSource: SemiFixExpensesDataSource,
) : DeleteSemiFixExpenseUseCase {

    override suspend fun invoke(params: SemiFixExpenseId): AppResult<Unit, Throwable> =
        semiFixExpensesDataSource.deleteSemiFixExpense(params)
}