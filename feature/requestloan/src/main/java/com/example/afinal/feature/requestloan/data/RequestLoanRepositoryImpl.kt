package com.example.afinal.feature.requestloan.data

import com.example.afinal.feature.requestloan.domain.RequestLoanRepository
import com.example.afinal.feature.requestloan.domain.RequestedLoan
import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.util.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestLoanRepositoryImpl @Inject constructor(
    private val service: RequestLoanApiService,
    private val dispatcher: CoroutineDispatcher
) : RequestLoanRepository {
    override suspend fun requestLoan(requestedLoan: RequestedLoan): NetworkResponse<Loan> =
        withContext(dispatcher) {
            var responseCode: Int? = null
            try {
                val response = service.postLoan(requestedLoan).execute()
                responseCode = response.code()
                NetworkResponse.Success(
                    response.body()
                        ?: throw IllegalStateException("Illegal response during request loan")
                )
            } catch (e: Exception) {
                NetworkResponse.Error(e, responseCode)
            }
        }
}