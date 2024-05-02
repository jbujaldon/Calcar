package com.calcar.common.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "staff")
data class StaffEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    val salary: Double,
    @ColumnInfo(name = "agreement_hours")
    val agreementHours: Double?,
    val profession: String,
)
