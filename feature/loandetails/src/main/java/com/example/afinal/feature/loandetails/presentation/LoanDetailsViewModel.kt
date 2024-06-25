package com.example.afinal.feature.loandetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afinal.feature.loandetails.LoanDetailsRouter
import com.example.afinal.feature.loandetails.domain.GetLoanDetailsUseCase
import com.example.afinal.util.NetworkResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoanDetailsViewModel @Inject constructor(
    private val router: LoanDetailsRouter,
    private val getLoanDetailsUseCase: GetLoanDetailsUseCase
) : ViewModel() {
    private val _state = MutableLiveData<LoanDetailsState>()
    val state: LiveData<LoanDetailsState> = _state

    fun close() = router.close()

    fun getLoanDetails(id: Int) = viewModelScope.launch {
        _state.value = LoanDetailsState.Loading
        val response = getLoanDetailsUseCase(id)
        if (response is NetworkResponse.Success) {
            _state.value = LoanDetailsState.Content(response.content)
        }
    }
}