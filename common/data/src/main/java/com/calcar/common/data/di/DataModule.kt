package com.calcar.common.data.di

import com.calcar.common.data.datasources.LocalSemiFixExpensesDataSource
import com.calcar.common.data.datasources.LocalStaffDataSource
import com.calcar.common.domain.semifixexpenses.datasource.SemiFixExpensesDataSource
import com.calcar.common.domain.staff.datasource.StaffDataSource
import org.koin.dsl.module

val dataModule = module {
    single<StaffDataSource> {
        LocalStaffDataSource(get())
    }
    single<SemiFixExpensesDataSource> {
        LocalSemiFixExpensesDataSource(get())
    }
}