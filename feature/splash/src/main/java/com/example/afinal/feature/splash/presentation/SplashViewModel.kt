package com.example.afinal.feature.splash.presentation

import androidx.lifecycle.ViewModel
import com.example.afinal.feature.splash.SplashRouter
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val router: SplashRouter,
    private val getTokenUseCase: com.example.afinal.feature.splash.domain.GetTokenUseCase
) : ViewModel() {

    fun checkToken() {
        if (getTokenUseCase() != null) {
            router.openHome()
        } else {
            router.openAuth()
        }
    }
}