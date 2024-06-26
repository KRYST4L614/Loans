package com.example.afinal.shared.fragmentDependencies

import androidx.lifecycle.ViewModelProvider

interface FragmentDependencies {
    val viewModelFactory: ViewModelProvider.Factory
    val localNavigationHolder: LocalNavigationHolder
}