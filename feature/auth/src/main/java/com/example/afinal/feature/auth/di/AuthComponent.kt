package com.example.afinal.feature.auth.di

import com.example.afinal.feature.auth.ui.AuthBottomSheet
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component

@Component(dependencies = [FragmentDependencies::class])
interface AuthComponent {

    fun inject(fragment: AuthBottomSheet)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): AuthComponent
    }
}
