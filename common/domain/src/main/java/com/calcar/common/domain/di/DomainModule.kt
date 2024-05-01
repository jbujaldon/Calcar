package com.calcar.common.domain.di

import com.calcar.common.domain.datasource.FakeStaffDataSource
import com.calcar.common.domain.usecases.SaveDirectStaffUseCase
import com.calcar.common.domain.usecases.SaveDirectStaffUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<SaveDirectStaffUseCase> {
        SaveDirectStaffUseCaseImpl(FakeStaffDataSource())
    }
}