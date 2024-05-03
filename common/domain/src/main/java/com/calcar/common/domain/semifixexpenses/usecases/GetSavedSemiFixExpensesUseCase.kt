package com.calcar.common.domain.semifixexpenses.usecases

import com.calcar.common.core.usecases.UseCase
import com.calcar.common.domain.semifixexpenses.datasource.SemiFixExpensesDataSource
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun interface GetSavedSemiFixExpensesUseCase : UseCase<Unit, Flow<List<SemiFixExpense>>>

class GetSavedSemiFixExpensesUseCaseImpl(
    private val semiFixExpensesDataSource: SemiFixExpensesDataSource,
) : GetSavedSemiFixExpensesUseCase {

    override fun invoke(params: Unit): Flow<List<SemiFixExpense>> =
        semiFixExpensesDataSource.getSavedSemiFixExpenses()
}