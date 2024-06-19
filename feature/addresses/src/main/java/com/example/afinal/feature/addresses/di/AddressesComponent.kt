package com.example.afinal.feature.addresses.di

import com.example.afinal.feature.addresses.ui.AddressesFragment
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.Component

@Component(dependencies = [FragmentDependencies::class])
interface AddressesComponent {

    fun inject(fragment: AddressesFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): AddressesComponent
    }
}