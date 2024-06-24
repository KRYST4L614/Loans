package com.example.afinal.feature.home.di

import com.example.afinal.feature.home.ui.HomeFragment
import com.example.afinal.feature.home.ui.HomePageFragment
import com.example.afinal.feature.home.ui.MenuPageFragment
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [FragmentDependencies::class], modules = [LocalNavigationModule::class])
interface HomeComponent {

    fun inject(fragment: HomeFragment)
    fun inject(fragment: HomePageFragment)

    fun inject(fragment: MenuPageFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): HomeComponent
    }
}