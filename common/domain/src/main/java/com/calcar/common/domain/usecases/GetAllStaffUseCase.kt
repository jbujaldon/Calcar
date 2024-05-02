package com.calcar.common.domain.usecases

import com.calcar.common.core.usecases.UseCase
import com.calcar.common.domain.datasource.StaffDataSource
import com.calcar.common.domain.entities.Staff
import kotlinx.coroutines.flow.Flow

fun interface GetAllStaffUseCase : UseCase<Unit, Flow<List<Staff>>>

class GetAllStaffUseCaseImpl(
    private val staffDataSource: StaffDataSource,
) : GetAllStaffUseCase {

    override fun invoke(params: Unit): Flow<List<Staff>> = staffDataSource.getAllStaff()
}