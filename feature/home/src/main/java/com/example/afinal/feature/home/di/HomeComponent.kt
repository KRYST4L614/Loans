package com.example.afinal.feature.home.di

import com.example.afinal.feature.home.ui.HomeFragment
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [FragmentDependencies::class])
interface HomeComponent {

    fun inject(fragment: HomeFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): HomeComponent
    }
}