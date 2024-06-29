package com.example.afinal.navigation

import com.example.afinal.feature.acceptloan.AcceptLoanRouter
import com.example.afinal.feature.addresses.getAddressesScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class AcceptLoanRouterImpl @Inject constructor(
    private val router: Router
) : AcceptLoanRouter {
    override fun close() = router.exit()

    override fun openAddresses() = router.replaceScreen(getAddressesScreen())
}