package com.exapmle.afinal.feature.language.di

import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import com.exapmle.afinal.feature.language.ui.LanguageFragment
import dagger.Component

@Component(dependencies = [FragmentDependencies::class])
interface LanguageComponent {

    fun inject(fragment: LanguageFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): LanguageComponent
    }
}