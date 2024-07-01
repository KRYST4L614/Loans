package com.example.afinal.feature.homepage.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afinal.component.resources.R
import com.example.afinal.feature.homepage.HomePageRouter
import com.example.afinal.feature.homepage.domain.GetLoanConditionsUseCase
import com.example.afinal.feature.homepage.presentation.HomePageState.Content
import com.example.afinal.feature.homepage.presentation.HomePageState.Error
import com.example.afinal.feature.homepage.presentation.HomePageState.Loading
import com.example.afinal.shared.loans.domain.GetLoansUseCase
import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.shared.loans.domain.entities.LoanConditions
import com.example.afinal.shared.resourceprovider.ResourceProvider
import com.example.afinal.util.NetworkResponse
import com.example.afinal.util.NetworkResponse.Success
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val router: HomePageRouter,
    private val getLoansUseCase: GetLoansUseCase,
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase,
    private val resourceProvider: ResourceProvider,
) : ViewModel() {

    private val _state: MutableLiveData<HomePageState> = MutableLiveData()
    val state get(): LiveData<HomePageState> = _state

    private var loansConditions: LoanConditions? = null

    fun openOnboarding() = router.openOnboarding()

    fun openMyLoansPage() = router.openMyLoans()

    fun openLoanDetails(loan: Loan) = router.openLoanDetails(loan)

    fun openRequestLoan(sum: Int) {
        loansConditions?.let {
            if (sum >= 500 && sum <= loansConditions!!.maxAmount)
                router.openRequestLoan(loansConditions!!.copy(maxAmount = sum))
        }
    }

    fun getLoansData() = viewModelScope.launch {
        _state.value = Loading
        val loansResponse = async { getLoansUseCase() }
        val loansConditionsResponse = async { getLoanConditionsUseCase() }

        val loans = loansResponse.await()
        val loansConditions = loansConditionsResponse.await()

        if (loans is Success && loansConditions is Success) {
            this@HomePageViewModel.loansConditions = loansConditions.content
            _state.value =
                Content(
                    loans.content.take(3),
                    loansConditions.content
                )
        } else {
            val errorResponse = if (loans is NetworkResponse.Error) {
                loans
            } else {
                loansConditions as NetworkResponse.Error
            }

            _state.value = Error(checkErrorResponse(errorResponse))
        }
    }

    private fun checkErrorResponse(response: NetworkResponse.Error): String {
        with(resourceProvider) {
            return when (response.code) {
                401 -> getString(R.string.unauthorized_error)

                403 -> getString(R.string.forbidden_error)

                404 -> getString(R.string.not_found_error)

                null -> {
                    when (response.e) {
                        is IllegalStateException -> getString(R.string.invalid_response_error)

                        is UnknownHostException -> getString(R.string.unknown_host_error)

                        else -> resourceProvider.getString(R.string.timeout_error)
                    }
                }

                else -> getString(R.string.common_error)
            }
        }
    }
}