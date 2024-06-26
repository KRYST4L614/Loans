package com.example.afinal.feature.acceptloan.di

import com.example.afinal.feature.acceptloan.ui.AcceptLoanFragment
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component

@Component(dependencies = [FragmentDependencies::class])
interface AcceptLoanComponent {

    fun inject(fragment: AcceptLoanFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): AcceptLoanComponent
    }
}
