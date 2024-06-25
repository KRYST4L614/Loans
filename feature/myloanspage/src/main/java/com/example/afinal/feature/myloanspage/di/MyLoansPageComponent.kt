package com.example.afinal.feature.myloanspage.di

import com.example.afinal.feature.myloanspage.ui.MyLoansPageFragment
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component

@Component(dependencies = [FragmentDependencies::class])
interface MyLoansPageComponent {
    fun inject(fragment: MyLoansPageFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): MyLoansPageComponent
    }
}