package com.example.afinal.navigation

import com.example.afinal.feature.home.getHomeScreen
import com.example.afinal.feature.rejectloan.RejectLoanRouter
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class RejectLoanRouterImpl @Inject constructor(
    private val router: Router
) : RejectLoanRouter {
    override fun close() = router.replaceScreen(getHomeScreen())
}