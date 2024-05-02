package com.calcar.common.domain.di

import com.calcar.common.domain.usecases.DeleteStaffUseCase
import com.calcar.common.domain.usecases.DeleteStaffUseCaseImpl
import com.calcar.common.domain.usecases.GetAllStaffUseCase
import com.calcar.common.domain.usecases.GetAllStaffUseCaseImpl
import com.calcar.common.domain.usecases.SaveDirectStaffUseCase
import com.calcar.common.domain.usecases.SaveDirectStaffUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<DeleteStaffUseCase> {
        DeleteStaffUseCaseImpl(get())
    }
    factory<GetAllStaffUseCase> {
        GetAllStaffUseCaseImpl(get())
    }
    factory<SaveDirectStaffUseCase> {
        SaveDirectStaffUseCaseImpl(get())
    }
}
