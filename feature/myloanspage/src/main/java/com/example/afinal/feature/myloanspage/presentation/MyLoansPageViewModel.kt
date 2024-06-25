package com.example.afinal.feature.myloanspage.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afinal.feature.myloanspage.MyLoansPageRouter
import com.example.afinal.feature.myloanspage.presentation.UIState.Content
import com.example.afinal.feature.myloanspage.presentation.UIState.Loading
import com.example.afinal.shared.loans.domain.GetLoansUseCase
import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.util.NetworkResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyLoansPageViewModel @Inject constructor(
    private val router: MyLoansPageRouter,
    private val getLoansUseCase: GetLoansUseCase
) : ViewModel() {

    private val _state: MutableLiveData<UIState> = MutableLiveData()
    val state get(): LiveData<UIState> = _state

    fun close() = router.close()

    fun openLoanDetails(loan: Loan) = router.openLoanDetails(loan)

    fun getLoans() = viewModelScope.launch {
        _state.value = Loading
        val response = getLoansUseCase()
        if (response is NetworkResponse.Success) {
            _state.value = Content(response.content)
        }
    }
}