package com.example.afinal.navigation

import com.example.afinal.di.LocalRouter
import com.example.afinal.feature.home.HomeRouter
import com.example.afinal.feature.homepage.getHomePageScreen
import com.example.afinal.feature.menupage.getMenuPageScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class HomeRouterImpl @Inject constructor(
    @LocalRouter private val router: Router
) : HomeRouter {
    override fun openHomePage() = router.newRootScreen(getHomePageScreen())
    override fun openMenuPage() = router.newRootScreen(getMenuPageScreen())
}