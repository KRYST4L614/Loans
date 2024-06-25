package com.example.afinal.navigation

import com.example.afinal.di.LocalRouter
import com.example.afinal.feature.home.LocalHomeRouter
import com.example.afinal.feature.homepage.getHomePageScreen
import com.example.afinal.feature.menupage.getMenuPageScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class LocalHomeRouterImpl @Inject constructor(
    @LocalRouter private val router: Router
) : LocalHomeRouter {
    override fun openHomePage() = router.navigateTo(getHomePageScreen())
    override fun openMenuPage() = router.navigateTo(getMenuPageScreen())
}