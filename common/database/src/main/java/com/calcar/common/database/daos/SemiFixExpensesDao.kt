package com.calcar.common.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.calcar.common.database.entities.SemiFixExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SemiFixExpensesDao {
    @Query("SELECT * FROM semi_fix_expenses")
    fun getAllSemiFixExpenses(): Flow<List<SemiFixExpenseEntity>>
}