package com.calcar.feature.onboarding.di

import com.calcar.feature.onboarding.onboarding.pages.OnboardingViewModel
import com.calcar.feature.onboarding.onboarding.staff.AddStaffViewModel
import com.calcar.feature.onboarding.welcome.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingModule = module {
    viewModel {
        WelcomeViewModel(get())
    }
    viewModel {
        OnboardingViewModel(get(), get(), get(), get(), get(), get())
    }
    viewModel {
        AddStaffViewModel(get(), get(), get())
    }
}