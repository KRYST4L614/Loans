package com.example.afinal.feature.homepage.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afinal.feature.homepage.HomePageRouter
import com.example.afinal.feature.homepage.presentation.UIState.Content
import com.example.afinal.feature.homepage.presentation.UIState.Loading
import com.example.afinal.shared.loans.domain.GetLoansUseCase
import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.util.NetworkResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val router: HomePageRouter,
    private val getLoansUseCase: GetLoansUseCase
) : ViewModel() {

    private val _state: MutableLiveData<UIState> = MutableLiveData()
    val state get(): LiveData<UIState> = _state

    fun openOnboarding() = router.openOnboarding()

    fun openMyLoansPage() = router.openMyLoans()

    fun openLoanDetails(loan: Loan) = router.openLoanDetails(loan)

    fun getLoans() = viewModelScope.launch {
        _state.value = Loading
        val response = getLoansUseCase()
        if (response is NetworkResponse.Success) {
            _state.value = Content(response.content)
        }
    }
}