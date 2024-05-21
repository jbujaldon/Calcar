package com.calcar.common.domain.staff.usecases

import com.calcar.common.core.result.AppResult
import com.calcar.common.core.usecases.UseCaseSuspend
import com.calcar.common.domain.staff.datasource.StaffDataSource
import com.calcar.common.domain.staff.entities.FullName
import com.calcar.common.domain.staff.entities.Profession
import com.calcar.common.domain.staff.entities.Salary
import com.calcar.common.domain.staff.entities.Staff
import com.calcar.common.domain.staff.entities.StaffId

fun interface SaveIndirectStaffUseCase :
    UseCaseSuspend<SaveIndirectStaffInput, AppResult<Unit, Throwable>>

data class SaveIndirectStaffInput(
    val name: String,
    val lastName: String,
    val salary: String,
    val profession: Profession,
)

internal class SaveIndirectStaffUseCaseImpl(
    private val staffDataSource: StaffDataSource,
) : SaveIndirectStaffUseCase {

    override suspend fun invoke(params: SaveIndirectStaffInput): AppResult<Unit, Throwable> =
        staffDataSource.saveStaff(params.toDomainStaff())

    private fun SaveIndirectStaffInput.toDomainStaff() = Staff.Indirect(
        id = StaffId.Empty,
        fullName = FullName(name, lastName),
        salary = Salary(salary.toDoubleOrNull()),
        profession = profession,
    )
}
