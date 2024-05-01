package com.calcar.feature.onboarding.ui.models

import androidx.annotation.StringRes
import com.calcar.common.ui.wrappers.ColorWrapper
import com.calcar.feature.onboarding.R

data class StaffId(val value: Long)

data class StaffUi(
    val id: StaffId,
    val fullName: String,
    val salary: Double,
    val profession: ProfessionUi,
)

enum class ProfessionUi(@StringRes val label: Int, val color: ColorWrapper) {
    Painter(R.string.work_profession_painter, ColorWrapper(0xFF0CBC8BL)),
    Mechanic(R.string.work_profession_mechanic, ColorWrapper(0xFFFD5D76L)),
    Platter(R.string.work_profession_platter, ColorWrapper(0xFF367CFFL)),
    Preparer(R.string.work_profession_preparer, ColorWrapper(0xFFECD500L)),
    Manager(R.string.work_profession_manager, ColorWrapper(0xFF6800ECL)),
    Administrative(R.string.work_profession_administrative, ColorWrapper(0xFF918DACL));

    fun isIndirect(): Boolean = this == Manager || this == Administrative
}