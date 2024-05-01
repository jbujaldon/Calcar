package com.calcar.feature.onboarding.welcome

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.calcar.feature.onboarding.R

enum class CarouselInformation(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
) {
    VisualizeData(
        image = R.drawable.data_report,
        title = R.string.visualize_data_title,
        description = R.string.visualize_data_description,
    ),
    VehiclesManagement(
        image = R.drawable.car_insurance,
        title = R.string.vehicles_management_title,
        description = R.string.vehicles_management_description,
    ),
    KeepTrackWork(
        image = R.drawable.mechanic,
        title = R.string.keep_track_work_title,
        description = R.string.keep_track_work_description,
    )
}
