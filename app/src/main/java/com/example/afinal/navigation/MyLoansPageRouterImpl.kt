package com.example.afinal.navigation

import com.example.afinal.di.LocalRouter
import com.example.afinal.feature.loandetails.getLoanDetailsScreen
import com.example.afinal.feature.myloanspage.MyLoansPageRouter
import com.example.afinal.shared.loans.domain.entities.Loan
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MyLoansPageRouterImpl @Inject constructor(
    @LocalRouter private val router: Router
) : MyLoansPageRouter {
    override fun close() = router.exit()
    override fun openLoanDetails(loan: Loan) = router.navigateTo(getLoanDetailsScreen(loan))

}