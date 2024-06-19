package com.example.afinal.feature.special.di

import com.example.afinal.feature.special.ui.SpecialFragment
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component

@Component(dependencies = [FragmentDependencies::class])
interface SpecialComponent {

    fun inject(fragment: SpecialFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): SpecialComponent
    }
}