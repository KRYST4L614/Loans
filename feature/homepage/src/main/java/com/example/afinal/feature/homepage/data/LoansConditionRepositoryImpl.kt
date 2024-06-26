package com.example.afinal.feature.homepage.data

import com.example.afinal.feature.homepage.domain.LoanConditionsRepository
import com.example.afinal.util.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoansConditionRepositoryImpl @Inject constructor(
    private val service: LoanConditionsApiService,
    private val dispatcher: CoroutineDispatcher
) : LoanConditionsRepository {
    override suspend fun getLoanConditions() = withContext(dispatcher) {
        var responseCode: Int? = null
        try {
            val response = service.getLoansConditions().execute()
            responseCode = response.code()
            NetworkResponse.Success(
                response.body()
                    ?: throw IllegalStateException("Illegal response during get loans conditions")
            )
        } catch (e: Exception) {
            NetworkResponse.Error(e, responseCode)
        }
    }

}