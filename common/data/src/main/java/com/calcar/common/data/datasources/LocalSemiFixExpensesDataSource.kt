package com.calcar.common.data.datasources

import com.calcar.common.core.result.AppResult
import com.calcar.common.core.result.appRunCatching
import com.calcar.common.core.wrappers.TextWrapper
import com.calcar.common.data.R
import com.calcar.common.database.daos.SemiFixExpensesDao
import com.calcar.common.database.entities.SemiFixExpenseEntity
import com.calcar.common.domain.semifixexpenses.datasource.SemiFixExpensesDataSource
import com.calcar.common.domain.semifixexpenses.entities.Amount
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpense
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpenseId
import com.calcar.common.domain.semifixexpenses.entities.SemiFixExpenseOption
import kotlinx.coroutines.Dispatchers
import kotlin.math.exp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocalSemiFixExpensesDataSource(
    private val semiFixExpensesDao: SemiFixExpensesDao,
) : SemiFixExpensesDataSource {

    override fun getSavedSemiFixExpenses(): Flow<List<SemiFixExpense>> = combine(
        semiFixExpensesDao.getAllSemiFixExpenses(),
        getAllSemiFixExpenseOptions()
    ) { savedSemiFixExpenses, semiFixExpenseOptions ->
        semiFixExpenseOptions
            .filter { option ->
                option.isContainedIn(savedSemiFixExpenses)
            }.zip(savedSemiFixExpenses) { option, expenseEntity ->
                option.toDomainSemiFixExpense(expenseEntity.amount)
            }
    }

    override fun getAllSemiFixExpenseOptions(): Flow<List<SemiFixExpenseOption>> =
        flowOf(semiFixExpenseOptions)

    override suspend fun saveNewSemiFixExpense(
        semiFixExpense: SemiFixExpense,
    ): AppResult<Unit, Throwable> = appRunCatching {
        withContext(Dispatchers.IO) {
            semiFixExpensesDao.insertSemiFixExpense(semiFixExpense.toDatabaseEntity())
        }
    }

    private fun SemiFixExpenseOption.isContainedIn(
        semiFixExpenses: List<SemiFixExpenseEntity>,
    ): Boolean = id.value in semiFixExpenses.map { it.id }
}

private fun SemiFixExpenseOption.toDomainSemiFixExpense(amount: Double): SemiFixExpense =
    SemiFixExpense(
        id = id,
        name = name,
        amount = Amount(amount)
    )

private fun SemiFixExpense.toDatabaseEntity() =
    SemiFixExpenseEntity(
        id = id.value,
        amount = amount.value,
    )

// TEMPORAL SOLUTION
private val semiFixExpenseOptions = listOf(
    SemiFixExpenseOption(
        id = SemiFixExpenseId(1L),
        name = TextWrapper.Resource(R.string.semi_fix_expense_office_material),
    ),
    SemiFixExpenseOption(
        id = SemiFixExpenseId(2L),
        name = TextWrapper.Resource(R.string.semi_fix_expense_phone),
    ),
    SemiFixExpenseOption(
        id = SemiFixExpenseId(3L),
        name = TextWrapper.Resource(R.string.semi_fix_expense_electricity),
    ),
    SemiFixExpenseOption(
        id = SemiFixExpenseId(4L),
        name = TextWrapper.Resource(R.string.semi_fix_expense_gas),
    ),
    SemiFixExpenseOption(
        id = SemiFixExpenseId(5L),
        name = TextWrapper.Resource(R.string.semi_fix_expense_social_insurances),
    ),
    SemiFixExpenseOption(
        id = SemiFixExpenseId(6L),
        name = TextWrapper.Resource(R.string.semi_fix_expense_hygienic_material),
    ),
    SemiFixExpenseOption(
        id = SemiFixExpenseId(7L),
        name = TextWrapper.Resource(R.string.semi_fix_expense_cleaning),
    ),
    SemiFixExpenseOption(
        id = SemiFixExpenseId(8L),
        name = TextWrapper.Resource(R.string.semi_fix_expense_work_dress),
    ),
    SemiFixExpenseOption(
        id = SemiFixExpenseId(9L),
        name = TextWrapper.Resource(R.string.semi_fix_expense_water),
    ),
    SemiFixExpenseOption(
        id = SemiFixExpenseId(10L),
        name = TextWrapper.Resource(R.string.semi_fix_expense_materials),
    ),
    SemiFixExpenseOption(
        id = SemiFixExpenseId(11L),
        name = TextWrapper.Resource(R.string.semi_fix_expense_trash),
    ),
    SemiFixExpenseOption(
        id = SemiFixExpenseId(12L),
        name = TextWrapper.Resource(R.string.semi_fix_expense_technology),
    ),
    SemiFixExpenseOption(
        id = SemiFixExpenseId(13L),
        name = TextWrapper.Resource(R.string.semi_fix_expense_nets),
    ),
    SemiFixExpenseOption(
        id = SemiFixExpenseId(14L),
        name = TextWrapper.Resource(R.string.semi_fix_expense_rent),
    ),
)
