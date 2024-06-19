package com.example.afinal.feature.support.presentation

import androidx.lifecycle.ViewModel
import com.example.afinal.feature.support.SupportRouter
import javax.inject.Inject

class SupportViewModel @Inject constructor(
    private val router: SupportRouter
) : ViewModel() {
    fun close() = router.close()
}