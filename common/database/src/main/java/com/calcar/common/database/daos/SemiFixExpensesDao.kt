package com.calcar.common.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.calcar.common.database.entities.SemiFixExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SemiFixExpensesDao {
    @Query("SELECT * FROM semi_fix_expenses")
    fun getAllSemiFixExpenses(): Flow<List<SemiFixExpenseEntity>>

    @Insert
    suspend fun insertSemiFixExpense(entity: SemiFixExpenseEntity)

    @Query("DELETE FROM semi_fix_expenses WHERE id = :id")
    suspend fun deleteSemiFixExpense(id: Long)
}