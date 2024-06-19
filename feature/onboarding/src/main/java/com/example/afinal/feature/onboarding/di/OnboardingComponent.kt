package com.example.afinal.feature.onboarding.di

import com.example.afinal.feature.onboarding.ui.OnboardingFragment
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component

@Component(dependencies = [FragmentDependencies::class])
interface OnboardingComponent {

    fun inject(fragment: OnboardingFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): OnboardingComponent
    }
}
