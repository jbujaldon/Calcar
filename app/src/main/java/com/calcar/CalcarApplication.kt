package com.calcar

import android.app.Application
import com.calcar.common.ui.di.uiModule
import com.calcar.dependencies.commonDependencies
import com.calcar.feature.onboarding.di.onboardingModule
import com.calcar.feature.vehicles.di.vehiclesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CalcarApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CalcarApplication)
            modules(onboardingModule, vehiclesModule, commonDependencies)
        }
    }
}