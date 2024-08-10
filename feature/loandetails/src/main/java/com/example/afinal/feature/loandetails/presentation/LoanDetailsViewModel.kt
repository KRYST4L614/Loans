package com.example.afinal.feature.loandetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afinal.feature.loandetails.LoanDetailsRouter
import com.example.afinal.feature.loandetails.domain.GetLoanDetailsUseCase
import com.example.afinal.feature.loandetails.presentation.LoanDetailsState.Error
import com.example.afinal.shared.loans.domain.entities.Status
import com.example.afinal.shared.resourceprovider.ResourceProvider
import com.example.afinal.util.NetworkResponse
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject
import com.example.afinal.component.resources.R as ComponentR
import com.example.afinal.shared.loans.R as SharedR

class LoanDetailsViewModel @Inject constructor(
    private val router: LoanDetailsRouter,
    private val getLoanDetailsUseCase: GetLoanDetailsUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _state = MutableLiveData<LoanDetailsState>()
    val state: LiveData<LoanDetailsState> = _state

    fun close() = router.close()

    fun getLoanDetails(id: Int) = viewModelScope.launch {
        _state.value = LoanDetailsState.Loading
        val response = getLoanDetailsUseCase(id)
        if (response is NetworkResponse.Success) {
            _state.value = LoanDetailsState.Content(response.content)
        } else {
            _state.value = Error(checkErrorResponse(response as NetworkResponse.Error))
        }
    }

    private fun checkErrorResponse(response: NetworkResponse.Error): String {
        with(resourceProvider) {
            return when (response.code) {
                401 -> getString(ComponentR.string.unauthorized_error)

                403 -> getString(ComponentR.string.forbidden_error)

                404 -> getString(ComponentR.string.not_found_error)

                null -> {
                    when (response.e) {
                        is IllegalStateException -> getString(ComponentR.string.invalid_response_error)

                        is UnknownHostException -> getString(ComponentR.string.unknown_host_error)

                        else -> getString(ComponentR.string.timeout_error)
                    }
                }

                else -> getString(ComponentR.string.common_error)
            }
        }
    }

    fun chooseStatusText(status: Status): String {
        with(resourceProvider) {
            return when (status) {
                Status.APPROVED -> {
                    getString(SharedR.string.loan_approved)
                }

                Status.REGISTERED -> {
                    getString(SharedR.string.loan_registered)
                }

                Status.GRANTED -> {
                    getString(SharedR.string.loan_rejected)
                }

                else -> {
                    throw IllegalStateException("Illegal state of loan")
                }
            }
        }
    }

    fun chooseStatusColor(status: Status): Int {
        with(resourceProvider) {
            return when (status) {
                Status.APPROVED -> {
                    getColor(SharedR.color.indicator_positive)
                }

                Status.REGISTERED -> {
                    getColor(SharedR.color.indicator_attention)
                }

                Status.GRANTED -> {
                    getColor(ComponentR.color.fontSecondaryDay)
                }

                else -> {
                    throw IllegalStateException("Illegal state of loan")
                }
            }
        }
    }
}