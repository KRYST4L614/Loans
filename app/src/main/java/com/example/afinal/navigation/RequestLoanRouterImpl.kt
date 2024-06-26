package com.example.afinal.navigation

import com.example.afinal.di.LocalRouter
import com.example.afinal.feature.acceptloan.getAcceptLoanScreen
import com.example.afinal.feature.rejectloan.getRejectLoanScreen
import com.example.afinal.feature.requestloan.RequestLoanRouter
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class RequestLoanRouterImpl @Inject constructor(
    @LocalRouter private val localRouter: Router,
    private val router: Router,
) : RequestLoanRouter {
    override fun close() = localRouter.exit()
    override fun openRejectLoan() = router.navigateTo(getRejectLoanScreen())
    override fun openAcceptLoan(sum: Int) = router.navigateTo(getAcceptLoanScreen(sum))
}