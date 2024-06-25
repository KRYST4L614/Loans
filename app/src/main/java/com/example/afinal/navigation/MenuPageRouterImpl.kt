package com.example.afinal.navigation

import com.example.afinal.di.LocalRouter
import com.example.afinal.feature.menupage.MenuPageRouter
import com.example.afinal.feature.special.getSpecialScreen
import com.example.afinal.feature.support.getSupportScreen
import com.exapmle.afinal.feature.language.getLanguageScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MenuPageRouterImpl @Inject constructor(
    @LocalRouter private val localRouter: Router,
    private val router: Router
) : MenuPageRouter {
    override fun openSupport() = router.navigateTo(getSupportScreen())

    override fun openLanguage() = router.navigateTo(getLanguageScreen())

    override fun openSpecial() = router.navigateTo(getSpecialScreen())
}