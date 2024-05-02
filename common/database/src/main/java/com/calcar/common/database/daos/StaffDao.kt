package com.calcar.common.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.calcar.common.database.entities.StaffEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StaffDao {
    @Query("SELECT * FROM staff")
    fun getAllStaff(): Flow<List<StaffEntity>>

    @Insert
    fun insertStaff(staff: StaffEntity)

    @Query("DELETE FROM staff WHERE id = :id")
    fun deleteStaff(id: Long)
}