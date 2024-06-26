package com.example.afinal.shared.fragmentDependencies

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router

data class LocalNavigationHolder(
    val cicerone: Cicerone<Router> = Cicerone.create(),
    val navigatorHolder: NavigatorHolder = cicerone.getNavigatorHolder(),
    val router: Router = cicerone.router
)