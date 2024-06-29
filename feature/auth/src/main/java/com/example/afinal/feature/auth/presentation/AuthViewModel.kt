package com.example.afinal.feature.auth.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afinal.feature.auth.AuthRouter
import com.example.afinal.feature.auth.R
import com.example.afinal.feature.auth.domain.entities.LoginUser
import com.example.afinal.feature.auth.domain.usecases.LoginUseCase
import com.example.afinal.feature.auth.domain.usecases.RegistrationUseCase
import com.example.afinal.feature.auth.domain.usecases.SaveTokenUseCase
import com.example.afinal.feature.auth.presentation.AuthState.Content
import com.example.afinal.feature.auth.presentation.AuthState.Loading
import com.example.afinal.shared.resourceprovider.ResourceProvider
import com.example.afinal.util.NetworkResponse
import com.example.afinal.util.NetworkResponse.Error
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject
import com.example.afinal.component.resources.R as ComponentR

class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val resourceProvider: ResourceProvider,
    private val router: AuthRouter
) : ViewModel() {

    private var _state: MutableLiveData<AuthState> = MutableLiveData()
    val state get(): LiveData<AuthState> = _state

    private var isLogin = true

    private var lastContent: Content = Content()

    init {
        saveTokenUseCase(null)
    }

    fun close() {
        if (_state.value !is Loading) {
            router.close()
        }
    }

    fun handleButtonClick(name: String, password: String, repeatedPassword: String) {
        if (isLogin) {
            login(name, password)
        } else {
            registration(name, password, repeatedPassword)
        }
    }

    private fun login(name: String, password: String) = viewModelScope.launch {
        checkPassword(password)
        checkName(name)

        if (lastContent == Content()) {
            _state.value = Loading

            val response = loginUseCase(LoginUser(name, password))

            if (response is NetworkResponse.Success) {
                saveTokenUseCase(response.content)

                if (isLogin) {
                    router.openHome()
                } else {
                    router.openOnboarding()
                }
            } else {
                _state.value = AuthState.Error(checkErrorResponse(response as Error))
                saveTokenUseCase(null)
            }
        }
    }

    private fun registration(name: String, password: String, repeatedPassword: String) =
        viewModelScope.launch {
            checkPasswords(password, repeatedPassword)
            checkName(name)

            if (lastContent == Content()) {
                _state.value = Loading

                val response = registrationUseCase(LoginUser(name, password))

                if (response is NetworkResponse.Success) {
                    login(name, password)
                } else {
                    _state.value = AuthState.Error(checkErrorResponse(response as Error))
                }
            }
            isLogin = true
        }

    private fun checkErrorResponse(errorResponse: Error): String {
        with(resourceProvider) {
            return when (errorResponse.code) {
                400 -> getString(R.string.user_already_exists)

                401 -> getString(ComponentR.string.unauthorized_error)

                403 -> getString(ComponentR.string.forbidden_error)

                404 -> getString(R.string.user_not_found)

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

    fun checkName(name: String) {
        val nameErrorMessage =
            if (name.isBlank()) {
                resourceProvider.getString(R.string.empty_login)
            } else if (!name.matches(Regex("^[a-zA-Z0-9]+\$"))) {
                resourceProvider.getString(R.string.only_latin_letters)
            } else {
                null
            }

        lastContent = lastContent.copy(
            nameErrorMessage = nameErrorMessage
        )

        _state.value = lastContent
    }

    fun checkPasswords(password: String, repeatedPassword: String) {
        checkPassword(password)

        if (!isLogin) {
            val repeatedPasswordErrorMessage =
                if (password != repeatedPassword) {
                    resourceProvider.getString(R.string.passwords_not_equals)
                } else {
                    null
                }

            lastContent = lastContent.copy(
                repeatedPasswordErrorMessage = repeatedPasswordErrorMessage
            )

            _state.value = lastContent
        }
    }

    private fun checkPassword(password: String) {
        val passwordErrorMessage =
            if (password.isBlank()) {
                resourceProvider.getString(R.string.empty_password)
            } else if (!password.matches(Regex("^[a-zA-Z0-9]+\$"))) {
                resourceProvider.getString(R.string.only_latin_letters_and_numbers)
            } else {
                null
            }

        lastContent = lastContent.copy(
            passwordErrorMessage = passwordErrorMessage
        )

        _state.value = lastContent
    }

    fun handleTabChange(position: Int) {
        isLogin = position == 0
    }

}