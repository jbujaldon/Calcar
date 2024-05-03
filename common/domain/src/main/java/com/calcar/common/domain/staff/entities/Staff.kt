package com.calcar.common.domain.staff.entities

sealed class StaffId(open val value: Long) {
    data object Empty : StaffId(-1L)
    data class Id(override val value: Long) : StaffId(value)
}

sealed class Staff {
    abstract val id: StaffId
    abstract val fullName: FullName
    abstract val salary: Salary
    abstract val profession: Profession

    data class Direct(
        override val id: StaffId,
        override val fullName: FullName,
        override val salary: Salary,
        override val profession: Profession,
        val agreementHours: AgreementHours,
    ) : Staff() {
        init {
            require(!profession.isIndirect()) { "Profession must be a direct type" }
        }
    }

    data class Indirect(
        override val id: StaffId,
        override val fullName: FullName,
        override val salary: Salary,
        override val profession: Profession
    ) : Staff() {
        init {
            require(profession.isIndirect()) { "Profession must be an indirect type" }
        }
    }
}

data class FullName(val name: String, val lastName: String) {
    init {
        require(name.isNotEmpty() && lastName.isNotEmpty()) {
            "Name and last name must not be empty"
        }
    }
}

data class Salary(val value: Double) {
    init {
        require(value > 0.0) { "Salary must be positive" }
    }
}

data class AgreementHours private constructor(val value: Double) {
    companion object {
        operator fun invoke(value: Double?): AgreementHours {
            if (value == null) {
                throw IllegalArgumentException("Value must not be null")
            }
            return AgreementHours(value)
        }
    }
}

enum class Profession {
    Mechanic,
    Painter,
    Preparer,
    Platter,
    Manager,
    Administrative;

    fun isIndirect(): Boolean = this == Manager || this == Administrative
}
