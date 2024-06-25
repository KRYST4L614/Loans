package com.example.afinal.navigation

import com.example.afinal.feature.auth.getAuthScreen
import com.example.afinal.feature.homepage.HomePageRouter
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class HomePageRouterImpl @Inject constructor(private val router: Router) : HomePageRouter {
    override fun openAuth() = router.replaceScreen(getAuthScreen())

}