package com.example.afinal.feature.rejectloan.presentation

import androidx.lifecycle.ViewModel
import com.example.afinal.feature.rejectloan.RejectLoanRouter
import javax.inject.Inject

class RejectLoanViewModel @Inject constructor(
    private val router: RejectLoanRouter
) : ViewModel() {
    fun close() = router.close()
}