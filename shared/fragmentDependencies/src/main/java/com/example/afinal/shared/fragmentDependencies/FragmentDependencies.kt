package com.example.afinal.shared.fragmentDependencies

import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router

interface FragmentDependencies {
    val viewModelFactory: ViewModelProvider.Factory
    val cicerone: Cicerone<Router>
    val router: Router
    val navigationHolder: NavigatorHolder
}