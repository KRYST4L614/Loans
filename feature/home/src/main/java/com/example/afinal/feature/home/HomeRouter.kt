package com.example.afinal.feature.home

import androidx.fragment.app.Fragment

interface HomeRouter {
    fun getMenuPageFragment(): Fragment
    fun getHomePageFragment(): Fragment
    fun openOnboarding()
    fun openSupport()
    fun openAuth()
    fun openLanguage()
    fun openSpecial()
}