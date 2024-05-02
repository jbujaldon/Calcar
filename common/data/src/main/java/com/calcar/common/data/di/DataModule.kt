package com.calcar.common.data.di

import com.calcar.common.data.datasources.LocalStaffDataSource
import com.calcar.common.domain.datasource.StaffDataSource
import org.koin.dsl.module

val dataModule = module {
    single<StaffDataSource> {
        LocalStaffDataSource(get())
    }
}