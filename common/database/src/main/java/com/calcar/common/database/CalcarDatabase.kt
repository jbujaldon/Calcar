package com.calcar.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.calcar.common.database.daos.SemiFixExpensesDao
import com.calcar.common.database.daos.StaffDao
import com.calcar.common.database.entities.SemiFixExpenseEntity
import com.calcar.common.database.entities.StaffEntity

@Database(
    entities = [
        StaffEntity::class,
        SemiFixExpenseEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class CalcarDatabase : RoomDatabase() {
    abstract fun staffDao(): StaffDao
    abstract fun semiFixExpensesDao(): SemiFixExpensesDao
}
