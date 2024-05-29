package com.calcar.common.domain.entities

data class FullName(val name: String, val lastName: String) {
    init {
        require(name.isNotEmpty() && lastName.isNotEmpty()) {
            "Name and last name must not be empty"
        }
    }

    override fun toString(): String = "$name $lastName"
}
