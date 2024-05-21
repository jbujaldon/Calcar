package com.calcar.common.domain.semifixexpenses.usecases

import com.calcar.common.core.usecases.UseCase
import com.calcar.common.domain.semifixexpenses.datasource.SemiFixExpensesDataSource
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpenseOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun interface GetAvailableSemiFixExpenseOptionsUseCase :
    UseCase<Unit, Flow<List<SemiFixExpenseOption>>>

class GetAvailableSemiFixExpenseOptionsUseCaseImpl(
    private val semiFixExpensesDataSource: SemiFixExpensesDataSource,
) : GetAvailableSemiFixExpenseOptionsUseCase {

    override fun invoke(params: Unit): Flow<List<SemiFixExpenseOption>> = combine(
        semiFixExpensesDataSource.getSavedSemiFixExpenses(),
        semiFixExpensesDataSource.getAllSemiFixExpenseOptions(),
    ) { savedExpenses, allOptions ->
        allOptions.filter { option ->
            val savedIds = savedExpenses.map { it.id }
            !savedIds.contains(option.id)
        }
    }
}
