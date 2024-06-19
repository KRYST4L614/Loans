package com.example.afinal

import androidx.lifecycle.ViewModel
import com.example.afinal.feature.auth.getAuthScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {
    fun onOnboarding() = router.newRootScreen(getAuthScreen())
}