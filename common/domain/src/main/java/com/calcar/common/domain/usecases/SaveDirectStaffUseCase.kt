package com.calcar.common.domain.usecases

import com.calcar.common.core.usecases.UseCaseSuspend
import com.calcar.common.core.result.AppResult
import com.calcar.common.core.result.appRunCatching
import com.calcar.common.domain.datasource.StaffDataSource
import com.calcar.common.domain.entities.AgreementHours
import com.calcar.common.domain.entities.FullName
import com.calcar.common.domain.entities.Profession
import com.calcar.common.domain.entities.Salary
import com.calcar.common.domain.entities.Staff
import com.calcar.common.domain.entities.StaffId

fun interface SaveDirectStaffUseCase :
    UseCaseSuspend<SaveDirectStaffInput, AppResult<Unit, Throwable>>

data class SaveDirectStaffInput(
    val name: String,
    val lastName: String,
    val salary: String,
    val hours: String,
    val profession: Profession,
)

internal class SaveDirectStaffUseCaseImpl(
    private val staffDataSource: StaffDataSource,
) : SaveDirectStaffUseCase {

    override suspend fun invoke(params: SaveDirectStaffInput): AppResult<Unit, Throwable> =
        appRunCatching {
            staffDataSource.saveStaff(params.toDomainStaff())
        }

    private fun SaveDirectStaffInput.toDomainStaff() = Staff.Direct(
        id = StaffId.Empty,
        fullName = FullName(name, lastName),
        salary = Salary(salary.toDouble()),
        profession = profession,
        agreementHours = AgreementHours(hours.toDouble()),
    )
}
