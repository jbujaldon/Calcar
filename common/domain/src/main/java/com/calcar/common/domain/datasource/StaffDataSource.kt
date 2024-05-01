package com.calcar.common.domain.datasource

import com.calcar.common.core.result.AppResult
import com.calcar.common.core.result.Success
import com.calcar.common.domain.entities.Staff

interface StaffDataSource {
    suspend fun saveStaff(staff: Staff): AppResult<Unit, Throwable>
}

class FakeStaffDataSource : StaffDataSource {
    override suspend fun saveStaff(staff: Staff): AppResult<Unit, Throwable> {
        return Success(Unit)
    }
}