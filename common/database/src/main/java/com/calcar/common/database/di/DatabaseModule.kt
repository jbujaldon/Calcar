package com.calcar.common.database.di

import androidx.room.Room
import com.calcar.common.database.CalcarDatabase
import com.calcar.common.database.daos.StaffDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single<CalcarDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            CalcarDatabase::class.java,
            "database.db"
        ).build()
    }
    single<StaffDao> {
        get<CalcarDatabase>().staffDao()
    }
}