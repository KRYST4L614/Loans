package com.example.afinal.navigation

import com.example.afinal.di.LocalRouter
import com.example.afinal.feature.loandetails.LoanDetailsRouter
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class LoanDetailsRouterImpl @Inject constructor(
    @LocalRouter private val router: Router
) : LoanDetailsRouter {
    override fun close() = router.exit()
}