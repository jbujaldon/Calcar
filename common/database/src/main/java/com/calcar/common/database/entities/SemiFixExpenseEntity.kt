package com.calcar.common.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "semi_fix_expenses")
data class SemiFixExpenseEntity(
    @PrimaryKey
    val id: Long,
    val amount: Double,
)