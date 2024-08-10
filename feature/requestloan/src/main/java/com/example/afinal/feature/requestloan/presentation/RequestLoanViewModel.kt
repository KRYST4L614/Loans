package com.example.afinal.feature.requestloan.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afinal.feature.requestloan.R
import com.example.afinal.feature.requestloan.RequestLoanRouter
import com.example.afinal.feature.requestloan.domain.RequestLoanUseCase
import com.example.afinal.feature.requestloan.domain.RequestedLoan
import com.example.afinal.feature.requestloan.presentation.RequestLoanState.Error
import com.example.afinal.feature.requestloan.presentation.RequestLoanState.Loading
import com.example.afinal.shared.loans.domain.entities.LoanConditions
import com.example.afinal.shared.loans.domain.entities.Status
import com.example.afinal.shared.resourceprovider.ResourceProvider
import com.example.afinal.util.NetworkResponse
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject
import com.example.afinal.component.resources.R as ComponentR

class RequestLoanViewModel @Inject constructor(
    private val router: RequestLoanRouter,
    private val requestLoanUseCase: RequestLoanUseCase,
    private val resourceProvider: ResourceProvider,
) : ViewModel() {

    private var lastContent = RequestLoanState.Content()

    private val _state = MutableLiveData<RequestLoanState>(lastContent)
    val state: LiveData<RequestLoanState> = _state

    fun close() = router.close()

    fun requestLoan(
        firstName: String,
        lastName: String,
        phone: String,
        loanConditions: LoanConditions?
    ) = viewModelScope.launch {
        if (loanConditions == null) {
            return@launch
        }

        checkFirstName(firstName)
        checkLastName(lastName)
        checkPhone(phone)

        if (lastContent == RequestLoanState.Content()) {
            _state.value = Loading
            val response = requestLoanUseCase(
                RequestedLoan(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phone,
                    period = loanConditions.period,
                    percent = loanConditions.percent,
                    amount = loanConditions.maxAmount,
                )
            )
            if (response is NetworkResponse.Success) {
                if (response.content.status == Status.REJECTED) {
                    router.openRejectLoan()
                } else {
                    router.openAcceptLoan(
                        response.content.amount.toInt(),
                        response.content.issueDate
                    )
                }
                close()
            } else {
                _state.value = Error(checkErrorResponse(response as NetworkResponse.Error))
            }
        }
    }

    private fun checkErrorResponse(errorResponse: NetworkResponse.Error): String {
        with(resourceProvider) {
            return when (errorResponse.code) {
                401 -> getString(ComponentR.string.unauthorized_error)

                403 -> getString(ComponentR.string.forbidden_error)

                404 -> getString(ComponentR.string.not_found_error)

                null -> {
                    when (errorResponse.e) {
                        is IllegalStateException ->
                            getString(ComponentR.string.invalid_response_error)

                        is UnknownHostException ->
                            getString(ComponentR.string.unknown_host_error)

                        else -> getString(ComponentR.string.timeout_error)
                    }
                }

                else -> getString(ComponentR.string.common_error)
            }
        }
    }

    fun checkPhone(phone: String) {
        val phoneNumberError = when {
            phone.isBlank() -> resourceProvider.getString(R.string.empty_phone)
            !isValidPhone(phone) -> resourceProvider.getString(R.string.invalid_phone)
            else -> null
        }

        lastContent = lastContent.copy(phoneNumberError = phoneNumberError)

        _state.value = lastContent
    }

    fun checkFirstName(firstName: String) {
        val firstNameErrorMessage = when {
            firstName.isBlank() -> resourceProvider.getString(R.string.empty_name)
            !isValidName(firstName) -> resourceProvider.getString(R.string.invalid_name)
            else -> null
        }

        lastContent = lastContent.copy(firstNameError = firstNameErrorMessage)

        _state.value = lastContent
    }

    fun checkLastName(lastName: String) {
        val lastNameErrorMessage = when {
            lastName.isBlank() -> resourceProvider.getString(R.string.empty_last_name)
            !isValidName(lastName) -> resourceProvider.getString(R.string.invalid_name)
            else -> null
        }

        lastContent = lastContent.copy(lastNameError = lastNameErrorMessage)

        _state.value = lastContent
    }


    private fun isValidPhone(phone: String): Boolean {
        val regex =
            Regex("^(\\+7|7|8)[\\s(-]*\\d{3}[)\\s-]*\\d{3}[\\s-]?\\d{2}[\\s-]?\\d{2}\$")

        return phone.matches(regex)
    }

    private fun isValidName(name: String): Boolean {
        val regex = Regex("^[А-Яа-я]+([\\s\\-][А-Яа-я]+)*\$")
        return name.matches(regex)
    }
}