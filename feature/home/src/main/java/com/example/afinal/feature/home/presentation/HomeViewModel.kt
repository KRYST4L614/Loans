package com.example.afinal.feature.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.afinal.feature.home.HomeRouter
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val router: HomeRouter,
) : ViewModel() {

    private var lastTabPosition: Int? = null

    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState> = _state

    init {
        handleTabChange(lastTabPosition ?: 0)
    }

    fun handleTabChange(position: Int) {
        if (lastTabPosition != position) {
            if (position == 0) {
                router.openHomePage()
                _state.value = HomeState.Content(position)
            } else {
                router.openMenuPage()
                _state.value = HomeState.Content(position)
            }
            lastTabPosition = position
        }
    }
}