package com.example.afinal.feature.rejectloan.di

import com.example.afinal.feature.rejectloan.ui.RejectLoanFragment
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component

@Component(dependencies = [FragmentDependencies::class])
interface RejectLoanComponent {

    fun inject(fragment: RejectLoanFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): RejectLoanComponent
    }
}
