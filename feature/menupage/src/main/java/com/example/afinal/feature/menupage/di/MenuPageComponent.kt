package com.example.afinal.feature.menupage.di

import com.example.afinal.feature.menupage.ui.MenuPageFragment
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [FragmentDependencies::class])
interface MenuPageComponent {

    fun inject(fragment: MenuPageFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): MenuPageComponent
    }
}