package com.example.afinal.shared.loans.data.repositories

import com.example.afinal.shared.loans.data.LoansApiService
import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.shared.loans.domain.repositories.LoansRepository
import com.example.afinal.util.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoansRepositoryImpl @Inject constructor(
    private val service: LoansApiService,
    private val dispatcher: CoroutineDispatcher
) : LoansRepository {
    override suspend fun getLoans(): NetworkResponse<List<Loan>> = withContext(dispatcher) {
        var responseCode: Int? = null
        try {
            val response = service.getLoans().execute()
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