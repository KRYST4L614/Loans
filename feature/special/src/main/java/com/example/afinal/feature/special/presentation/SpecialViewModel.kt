package com.example.afinal.feature.special.presentation

import androidx.lifecycle.ViewModel
import com.example.afinal.feature.special.SpecialRouter
import javax.inject.Inject

class SpecialViewModel @Inject constructor(
    private val router: SpecialRouter
) : ViewModel() {
    fun close() = router.close()
}