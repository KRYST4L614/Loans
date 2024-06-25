package com.example.afinal.feature.homepage.di

import com.example.afinal.feature.homepage.ui.HomePageFragment
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [FragmentDependencies::class])
interface HomePageComponent {

    fun inject(fragment: HomePageFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): HomePageComponent
    }
}