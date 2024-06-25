package com.example.afinal.feature.menupage.presentation

import androidx.lifecycle.ViewModel
import com.example.afinal.feature.menupage.MenuPageRouter
import javax.inject.Inject

class MenuPageViewModel @Inject constructor(
    private val router: MenuPageRouter
) : ViewModel() {
    fun openSupport() = router.openSupport()
    fun openLanguage() = router.openLanguage()
    fun openSpecial() = router.openSpecial()
}