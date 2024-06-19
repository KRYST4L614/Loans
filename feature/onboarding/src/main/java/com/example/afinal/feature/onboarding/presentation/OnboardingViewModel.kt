package com.example.afinal.feature.onboarding.presentation

import androidx.lifecycle.ViewModel
import com.example.afinal.feature.onboarding.OnboardingRouter
import javax.inject.Inject

class OnboardingViewModel @Inject constructor(
    private val router: OnboardingRouter
) : ViewModel() {
    fun openHomePage() = router.openHome()
}