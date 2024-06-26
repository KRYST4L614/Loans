package com.example.afinal.feature.myloanspage.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afinal.component.resources.R
import com.example.afinal.feature.myloanspage.MyLoansPageRouter
import com.example.afinal.feature.myloanspage.presentation.MyLoansPageState.Content
import com.example.afinal.feature.myloanspage.presentation.MyLoansPageState.Error
import com.example.afinal.feature.myloanspage.presentation.MyLoansPageState.Loading
import com.example.afinal.shared.loans.domain.GetLoansUseCase
import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.shared.resourceprovider.ResourceProvider
import com.example.afinal.util.NetworkResponse
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

class MyLoansPageViewModel @Inject constructor(
    private val router: MyLoansPageRouter,
    private val getLoansUseCase: GetLoansUseCase,
    private val resourceProvider: ResourceProvider,
) : ViewModel() {

    private val _state: MutableLiveData<MyLoansPageState> = MutableLiveData()
    val state get(): LiveData<MyLoansPageState> = _state

    fun close() = router.close()

    fun openLoanDetails(loan: Loan) = router.openLoanDetails(loan)

    fun getLoans() = viewModelScope.launch {
        _state.value = Loading
        val response = getLoansUseCase()
        if (response is NetworkResponse.Success) {
            _state.value = Content(response.content)
        } else {
            _state.value = Error(checkErrorResponse(response as NetworkResponse.Error))
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

                        else -> getString(R.string.timeout_error)
                    }
                }

                else -> getString(R.string.common_error)
            }
        }
    }
}