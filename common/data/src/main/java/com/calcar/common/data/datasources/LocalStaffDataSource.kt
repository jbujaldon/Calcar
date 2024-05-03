package com.calcar.common.data.datasources

import com.calcar.common.core.result.AppResult
import com.calcar.common.core.result.appRunCatching
import com.calcar.common.database.daos.StaffDao
import com.calcar.common.database.entities.StaffEntity
import com.calcar.common.domain.staff.datasource.StaffDataSource
import com.calcar.common.domain.staff.entities.AgreementHours
import com.calcar.common.domain.staff.entities.FullName
import com.calcar.common.domain.staff.entities.Profession
import com.calcar.common.domain.staff.entities.Salary
import com.calcar.common.domain.staff.entities.Staff
import com.calcar.common.domain.staff.entities.StaffId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocalStaffDataSource(private val staffDao: StaffDao) : StaffDataSource {

    override fun getAllStaff(): Flow<List<Staff>> = staffDao.getAllStaff().map {
        it.map(StaffEntity::toDomain)
    }

    override suspend fun saveStaff(staff: Staff): AppResult<Unit, Throwable> =
        appRunCatching {
            withContext(Dispatchers.IO) {
                staffDao.insertStaff(staff.toDatabaseEntity())
            }
        }

    override suspend fun deleteStaff(id: StaffId.Id): AppResult<Unit, Throwable> =
        appRunCatching {
            withContext(Dispatchers.IO) {
                staffDao.deleteStaff(id.value)
            }
        }
}

private fun StaffEntity.toDomain(): Staff {
    val profession = Profession.valueOf(profession)
    return if (profession.isIndirect()) {
        Staff.Indirect(
            id = StaffId.Id(id),
            fullName = FullName(name, lastName),
            salary = Salary(salary),
            profession = profession,
        )
    } else {
        Staff.Direct(
            id = StaffId.Id(id),
            fullName = FullName(name, lastName),
            salary = Salary(salary),
            profession = profession,
            agreementHours = AgreementHours(agreementHours),
        )
    }
}

private fun Staff.toDatabaseEntity(): StaffEntity =
    StaffEntity(
        id = id.value,
        name = fullName.name,
        lastName = fullName.lastName,
        salary = salary.value,
        profession = profession.name,
        agreementHours = when (this) {
            is Staff.Direct -> agreementHours.value
            is Staff.Indirect -> null
        }
    )