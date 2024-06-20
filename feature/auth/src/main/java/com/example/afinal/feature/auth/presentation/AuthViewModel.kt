package com.example.afinal.feature.auth.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afinal.feature.auth.AuthRouter
import com.example.afinal.feature.auth.domain.entities.LoginUser
import com.example.afinal.feature.auth.domain.entities.NetworkResponse
import com.example.afinal.feature.auth.domain.usecases.LoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val router: AuthRouter
) : ViewModel() {

    private var _state: MutableLiveData<AuthState> = MutableLiveData()
    val state get(): LiveData<AuthState> = _state

    fun login(name: String, password: String) = viewModelScope.launch {
        _state.value = AuthState.Loading
        val response = loginUseCase(LoginUser(name, password))
        if (response is NetworkResponse.Success) {
            _state.value = AuthState.Success
            router.openOnboarding()
        } else {
            _state.value =
                AuthState.Error((response as NetworkResponse.Error).t.localizedMessage ?: "")
        }
    }

}