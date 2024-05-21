package com.calcar.common.domain.di

import com.calcar.common.domain.semifixexpenses.usecases.GetAvailableSemiFixExpenseOptionsUseCase
import com.calcar.common.domain.semifixexpenses.usecases.GetAvailableSemiFixExpenseOptionsUseCaseImpl
import com.calcar.common.domain.semifixexpenses.usecases.GetSavedSemiFixExpensesUseCase
import com.calcar.common.domain.semifixexpenses.usecases.GetSavedSemiFixExpensesUseCaseImpl
import com.calcar.common.domain.semifixexpenses.usecases.SaveSemiFixExpenseUseCase
import com.calcar.common.domain.semifixexpenses.usecases.SaveSemiFixExpenseUseCaseImpl
import com.calcar.common.domain.staff.usecases.DeleteStaffUseCase
import com.calcar.common.domain.staff.usecases.DeleteStaffUseCaseImpl
import com.calcar.common.domain.staff.usecases.GetAllStaffUseCase
import com.calcar.common.domain.staff.usecases.GetAllStaffUseCaseImpl
import com.calcar.common.domain.staff.usecases.SaveDirectStaffUseCase
import com.calcar.common.domain.staff.usecases.SaveDirectStaffUseCaseImpl
import com.calcar.common.domain.staff.usecases.SaveIndirectStaffUseCase
import com.calcar.common.domain.staff.usecases.SaveIndirectStaffUseCaseImpl
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
    factory<SaveIndirectStaffUseCase> {
        SaveIndirectStaffUseCaseImpl(get())
    }
    factory<GetSavedSemiFixExpensesUseCase> {
        GetSavedSemiFixExpensesUseCaseImpl(get())
    }
    factory<SaveSemiFixExpenseUseCase> {
        SaveSemiFixExpenseUseCaseImpl(get())
    }
    factory<GetAvailableSemiFixExpenseOptionsUseCase> {
        GetAvailableSemiFixExpenseOptionsUseCaseImpl(get())
    }
}
