package com.example.afinal.feature.addresses.presentation

import androidx.lifecycle.ViewModel
import com.example.afinal.feature.addresses.AddressesRouter
import javax.inject.Inject

class AddressesViewModel @Inject constructor(
    private val router: AddressesRouter
) : ViewModel() {
    fun close() = router.close()
}