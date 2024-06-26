package com.example.afinal.feature.requestloan

interface RequestLoanRouter {
    fun close()
    fun openRejectLoan()
    fun openAcceptLoan(sum: Int)
}