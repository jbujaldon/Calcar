package com.calcar.common.domain.entities

sealed class StaffId {
    data object Empty : StaffId()
    data class Id(val value: Long) : StaffId()
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

data class AgreementHours(val value: Double) {
    init {
        require(value > 0.0) { "Agreement hours must be positive" }
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
