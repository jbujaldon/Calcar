package com.calcar.common.data.di

import androidx.datastore.core.DataStore
import com.calcar.common.data.datasources.LocalGarageDataSource
import com.calcar.common.data.datasources.LocalSemiFixExpensesDataSource
import com.calcar.common.data.datasources.LocalStaffDataSource
import com.calcar.common.data.datasources.LocalUserDataSource
import com.calcar.common.datastore.GarageInformationPreferences
import com.calcar.common.datastore.UserPreferences
import com.calcar.common.domain.garage.datasource.GarageDataSource
import com.calcar.common.domain.semifixexpenses.datasource.SemiFixExpensesDataSource
import com.calcar.common.domain.staff.datasource.StaffDataSource
import com.calcar.common.domain.user.datasources.UserDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<StaffDataSource> {
        LocalStaffDataSource(get())
    }
    single<SemiFixExpensesDataSource> {
        LocalSemiFixExpensesDataSource(get())
    }
    single<GarageDataSource> {
        LocalGarageDataSource(get(named("GarageDataStore")))
    }
    single<UserDataSource> {
        LocalUserDataSource(get(named("UserDataStore")))
    }
}