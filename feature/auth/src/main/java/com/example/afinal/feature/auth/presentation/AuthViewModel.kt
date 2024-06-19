package com.example.afinal.feature.auth.presentation

import androidx.lifecycle.ViewModel
import com.example.afinal.feature.auth.AuthRouter
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val router: AuthRouter
) : ViewModel() {
    fun openOnboarding() = router.openOnboarding()
}