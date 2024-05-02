package com.calcar.common.domain.usecases

import com.calcar.common.core.result.AppResult
import com.calcar.common.core.usecases.UseCaseSuspend
import com.calcar.common.domain.datasource.StaffDataSource
import com.calcar.common.domain.entities.StaffId

fun interface DeleteStaffUseCase : UseCaseSuspend<StaffId.Id, AppResult<Unit, Throwable>>

class DeleteStaffUseCaseImpl(
    private val staffDataSource: StaffDataSource,
) : DeleteStaffUseCase {

    override suspend fun invoke(params: StaffId.Id): AppResult<Unit, Throwable> =
        staffDataSource.deleteStaff(params)
}