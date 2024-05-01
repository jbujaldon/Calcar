package com.calcar.common.ui.di

import com.calcar.common.ui.navigation.Navigator
import org.koin.dsl.module

val uiModule = module {
    single {
        Navigator()
    }
}