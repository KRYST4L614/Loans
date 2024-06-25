package com.example.afinal.feature.loandetails.data

import com.example.afinal.feature.loandetails.domain.LoanDetailsRepository
import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.util.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoanDetailsRepositoryImpl @Inject constructor(
    private val service: LoanDetailsApiService,
    private val dispatcher: CoroutineDispatcher
) : LoanDetailsRepository {
    override suspend fun getLoanDetails(id: Int): NetworkResponse<Loan> = withContext(dispatcher) {
        var responseCode: Int? = null
        try {
            val response = service.getLoanDetails(id).execute()
            responseCode = response.code()
            NetworkResponse.Success(
                response.body()
                    ?: throw IllegalStateException("Illegal response during get loans")
            )
        } catch (e: Exception) {
            NetworkResponse.Error(e, responseCode)
        }
    }
}