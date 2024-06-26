package com.example.afinal.feature.splash.di

import com.example.afinal.feature.splash.ui.SplashFragment
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component

@Component(dependencies = [FragmentDependencies::class])
interface SplashComponent {

    fun inject(fragment: SplashFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): SplashComponent
    }
}
