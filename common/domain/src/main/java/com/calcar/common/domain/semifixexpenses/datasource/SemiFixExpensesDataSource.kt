package com.calcar.common.domain.semifixexpenses.datasource

import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpense
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpenseOption
import kotlinx.coroutines.flow.Flow

interface SemiFixExpensesDataSource {
    fun getSavedSemiFixExpenses(): Flow<List<SemiFixExpense>>
    fun getAllSemiFixExpenseOptions(): Flow<List<SemiFixExpenseOption>>
}