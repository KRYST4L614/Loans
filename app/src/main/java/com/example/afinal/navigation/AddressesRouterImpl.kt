package com.example.afinal.navigation

import com.example.afinal.feature.addresses.AddressesRouter
import com.example.afinal.feature.home.getHomeScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class AddressesRouterImpl @Inject constructor(
    private val router: Router
) : AddressesRouter {
    override fun close() = router.backTo(getHomeScreen())
}