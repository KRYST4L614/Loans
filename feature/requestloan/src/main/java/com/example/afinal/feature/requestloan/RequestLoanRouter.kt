package com.example.afinal.feature.requestloan

import java.util.Date

interface RequestLoanRouter {
    fun close()
    fun openRejectLoan()
    fun openAcceptLoan(sum: Int, issueDate: Date)
}