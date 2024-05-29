package com.calcar.feature.vehicles.list

import com.calcar.common.ui.models.Tab
import com.calcar.feature.vehicles.R

enum class VehicleTab(override val labelRes: Int) : Tab {
    Budgeted(R.string.budgeted_tab),
    Repairing(R.string.repairing_tab),
    Finished(R.string.finished_tab),
}