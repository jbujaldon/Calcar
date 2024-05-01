package com.calcar.dependencies

import com.calcar.common.domain.di.domainModule
import com.calcar.common.ui.di.uiModule
import org.koin.dsl.module

val commonDependencies = module {
    includes(domainModule, uiModule)
}
