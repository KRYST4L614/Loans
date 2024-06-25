package com.example.afinal.feature.loandetails.di

import com.example.afinal.feature.loandetails.ui.LoanDetailsFragment
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component

@Component(dependencies = [FragmentDependencies::class])
interface LoanDetailsComponent {
    fun inject(fragment: LoanDetailsFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): LoanDetailsComponent
    }
}