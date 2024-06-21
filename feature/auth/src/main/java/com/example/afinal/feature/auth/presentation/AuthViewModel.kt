package com.example.afinal.feature.auth.presentation

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afinal.feature.auth.AuthRouter
import com.example.afinal.feature.auth.domain.entities.LoginUser
import com.example.afinal.feature.auth.domain.usecases.LoginUseCase
import com.example.afinal.feature.auth.domain.usecases.RegistrationUseCase
import com.example.afinal.util.NetworkResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val router: AuthRouter
) : ViewModel() {

    private var _state: MutableLiveData<AuthState> = MutableLiveData()
    val state get(): LiveData<AuthState> = _state

    private var isLogin = true

    fun handleButtonClick(name: Editable?, password: Editable?) {
        if (isLogin) {
            login(name, password)
        } else {
            registration(name, password)
        }
    }

    private fun login(name: Editable?, password: Editable?) = viewModelScope.launch {
        checkPassword(password)
        checkName(name)
        if ((_state.value as AuthState.Content) == AuthState.Content(isLogin) && name != null && password != null) {
            _state.value = AuthState.Loading
            val response = loginUseCase(LoginUser(name.toString(), password.toString()))
            if (response is NetworkResponse.Success) {
                _state.value = AuthState.Success
                router.openOnboarding()
            } else {
                _state.value =
                    AuthState.Content(isLogin)
            }
        }
    }

    private fun registration(name: Editable?, password: Editable?) = viewModelScope.launch {
        checkPassword(password)
        checkName(name)
        val a = _state.value as AuthState.Content
        Log.d("assasd", a.toString())
        Log.d("assads", AuthState.Content(isLogin).toString())
        if ((_state.value as AuthState.Content) == AuthState.Content(isLogin) && name != null && password != null) {
            _state.value = AuthState.Loading
            val response = registrationUseCase(LoginUser(name.toString(), password.toString()))
            if (response is NetworkResponse.Success) {
                login(name, password)
            } else {
                _state.value = AuthState.Error(
                    (response as NetworkResponse.Error).e.localizedMessage ?: "",
                )
            }
        }
    }

    fun checkName(name: Editable?) {
        val nameString = (name ?: "")
        val nameErrorMessage =
            if (nameString.trim().isBlank()) {
                "Имя не может быть пустым"
            } else if (!nameString.matches(Regex("^[a-zA-Z0-9]+\$"))) {
                "Только латинские буквы и цифры"
            } else {
                null
            }
        if (_state.value is AuthState.Content) {
            _state.value = (_state.value as AuthState.Content).copy(
                isLogin = isLogin,
                nameErrorMessage = nameErrorMessage
            )
        } else {
            _state.value = AuthState.Content(isLogin, nameErrorMessage = nameErrorMessage)
        }
    }

    fun checkPasswords(password: Editable?, repeatedPassword: Editable?) {
        checkPassword(password)
        if (!isLogin) {
            val repeatedPasswordErrorMessage =
                if (password != null && repeatedPassword != null && password.toString() != repeatedPassword.toString()) {
                    "Пароли не совпадают"
                } else {
                    null
                }
            if (_state.value is AuthState.Content) {
                _state.value = (_state.value as AuthState.Content).copy(
                    isLogin = isLogin,
                    repeatedPasswordErrorMessage = repeatedPasswordErrorMessage
                )
            } else {
                _state.value = AuthState.Content(isLogin, repeatedPasswordErrorMessage)
            }
        }
    }

    private fun checkPassword(password: Editable?) {
        val passwordString = (password ?: "")
        val passwordErrorMessage =
            if (passwordString.trim().isBlank()) {
                "Пароль не может быть пустым"
            } else if (!passwordString.matches(Regex("^[a-zA-Z0-9]+\$"))) {
                "Только латинские буквы и цифры"
            } else {
                null
            }
        if (_state.value is AuthState.Content) {
            _state.value = (_state.value as AuthState.Content).copy(
                isLogin = isLogin,
                passwordErrorMessage = passwordErrorMessage
            )
        } else {
            _state.value = AuthState.Content(isLogin, passwordErrorMessage = passwordErrorMessage)
        }
    }

    fun handleTabChange(position: Int) {
        isLogin = position == 0
    }

}