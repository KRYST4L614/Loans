package com.example.afinal.feature.acceptloan.presentation

import androidx.lifecycle.ViewModel
import com.example.afinal.feature.acceptloan.AcceptLoanRouter
import javax.inject.Inject

class AcceptLoanViewModel @Inject constructor(
    private val router: AcceptLoanRouter
) : ViewModel() {
    fun close() = router.close()

    fun openAddresses() = router.openAddresses()
}