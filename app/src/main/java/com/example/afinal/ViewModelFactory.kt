package com.example.afinal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val providersMap: Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider = providersMap[modelClass]
        val viewModel =
            provider?.get() ?: throw RuntimeException("Unsupported viewmodel type: $modelClass")
        return viewModel as T
    }
}