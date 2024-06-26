package com.example.afinal.feature.requestloan.di

import com.example.afinal.feature.requestloan.ui.RequestLoanFragment
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component

@Component(dependencies = [FragmentDependencies::class])
interface RequestLoanComponent {
    fun inject(fragment: RequestLoanFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): RequestLoanComponent
    }
}