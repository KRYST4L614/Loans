package com.example.afinal.feature.menupage.presentation

import androidx.lifecycle.ViewModel
import com.example.afinal.feature.menupage.MenuPageRouter
import com.example.afinal.feature.menupage.R
import com.example.afinal.shared.resourceprovider.ResourceProvider
import javax.inject.Inject

class MenuPageViewModel @Inject constructor(
    private val router: MenuPageRouter,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    fun openOnboarding() = router.openOnboarding()
    fun openAuth() = router.openAuth()

    fun handleClickMenuItem(itemName: String) {
        with(resourceProvider) {
            when (itemName) {
                getString(R.string.my_loans) -> router.openMyLoansPage()
                getString(R.string.support) -> router.openSupport()
                getString(R.string.special) -> router.openSpecial()
                getString(R.string.language) -> router.openLanguage()
                getString(R.string.addresses) -> router.openAddresses()
            }
        }
    }
}