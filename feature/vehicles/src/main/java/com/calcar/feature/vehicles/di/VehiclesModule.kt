package com.calcar.feature.vehicles.di

import com.calcar.feature.vehicles.list.VehiclesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val vehiclesModule = module {
    viewModelOf(::VehiclesViewModel)
}
