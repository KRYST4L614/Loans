package com.example.afinal.feature.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afinal.feature.home.HomeRouter
import com.example.afinal.feature.home.R
import com.example.afinal.feature.home.domain.LoansRepository
import com.example.afinal.feature.home.presentation.UIState.Content
import com.example.afinal.feature.home.presentation.UIState.Loading
import com.example.afinal.util.NetworkResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val router: HomeRouter,
    private val repository: LoansRepository
) : ViewModel() {

    private val _state: MutableLiveData<UIState> = MutableLiveData()
    val state get(): LiveData<UIState> = _state

    val viewPagerFragments = listOf(
        router::getMenuPageFragment,
        router::getHomePageFragment
        )
    fun openOnboarding() = router.openOnboarding()

    fun openLanguage() = router.openLanguage()

    fun openSpecial() = router.openSpecial()

    fun openSupport() = router.openSupport()

    fun getLoans() = viewModelScope.launch {
        _state.value = Loading
        val response = repository.getLoans()
        if (response is NetworkResponse.Success) {
            _state.value = Content(response.content, "Главная")
        }
    }

    fun handleTabChange(position: Int) {
        _state.value = Content(emptyList(), if (position == 0) "Главная" else "Меню")
    }
}