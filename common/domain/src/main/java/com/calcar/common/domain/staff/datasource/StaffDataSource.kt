package com.calcar.common.domain.staff.datasource

import com.calcar.common.core.result.AppResult
import com.calcar.common.core.result.Success
import com.calcar.common.domain.staff.entities.Staff
import com.calcar.common.domain.staff.entities.StaffId
import kotlinx.coroutines.flow.Flow

interface StaffDataSource {
    fun getAllStaff(): Flow<List<Staff>>
    suspend fun saveStaff(staff: Staff): AppResult<Unit, Throwable>
    suspend fun deleteStaff(id: StaffId.Id): AppResult<Unit, Throwable>
}
