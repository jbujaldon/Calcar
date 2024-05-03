package com.calcar.common.domain.di

import com.calcar.common.domain.semifixexpenses.usecases.GetSavedSemiFixExpensesUseCase
import com.calcar.common.domain.semifixexpenses.usecases.GetSavedSemiFixExpensesUseCaseImpl
import com.calcar.common.domain.staff.usecases.DeleteStaffUseCase
import com.calcar.common.domain.staff.usecases.DeleteStaffUseCaseImpl
import com.calcar.common.domain.staff.usecases.GetAllStaffUseCase
import com.calcar.common.domain.staff.usecases.GetAllStaffUseCaseImpl
import com.calcar.common.domain.staff.usecases.SaveDirectStaffUseCase
import com.calcar.common.domain.staff.usecases.SaveDirectStaffUseCaseImpl
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
    factory<GetSavedSemiFixExpensesUseCase> {
        GetSavedSemiFixExpensesUseCaseImpl(get())
    }
}
