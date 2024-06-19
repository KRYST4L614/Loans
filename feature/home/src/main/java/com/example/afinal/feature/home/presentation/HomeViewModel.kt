package com.example.afinal.feature.home.presentation

import androidx.lifecycle.ViewModel
import com.example.afinal.feature.home.HomeRouter
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val router: HomeRouter
) : ViewModel() {

    val menuScreen = router
    fun openOnboarding() = router.openOnboarding()

    fun openLanguage() = router.openLanguage()

    fun openSpecial() = router.openSpecial()

    fun openSupport() = router.openSupport()
}