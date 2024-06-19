package com.example.afinal.navigation

import com.example.afinal.feature.support.SupportRouter
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class SupportRouterImpl @Inject constructor(private val router: Router) : SupportRouter {
    override fun close() = router.exit()
}