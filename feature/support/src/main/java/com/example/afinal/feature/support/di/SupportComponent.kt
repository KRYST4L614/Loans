package com.example.afinal.feature.support.di

import com.example.afinal.feature.support.ui.SupportFragment
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component

@Component(dependencies = [FragmentDependencies::class])
interface SupportComponent {

    fun inject(fragment: SupportFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): SupportComponent
    }
}