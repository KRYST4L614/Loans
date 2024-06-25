package com.example.afinal.feature.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.afinal.feature.home.HomeRouter
import com.example.afinal.feature.home.LocalHomeRouter
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val router: HomeRouter,
    private val localRouter: LocalHomeRouter,
) : ViewModel() {

    private var lastTabPosition = 0

    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState> = _state
    fun openOnboarding() = router.openOnboarding()

    fun handleTabChange(position: Int = lastTabPosition) {
        if (position == 0) {
            localRouter.openHomePage()
            _state.value = HomeState.Content("Главная", position)
        } else {
            localRouter.openMenuPage()
            _state.value = HomeState.Content("Меню", position)
        }
        lastTabPosition = position
    }
}