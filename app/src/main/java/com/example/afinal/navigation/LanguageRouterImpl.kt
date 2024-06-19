package com.example.afinal.navigation

import com.exapmle.afinal.feature.language.LanguageRouter
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class LanguageRouterImpl @Inject constructor(private val router: Router) : LanguageRouter {
    override fun close() = router.exit()
}