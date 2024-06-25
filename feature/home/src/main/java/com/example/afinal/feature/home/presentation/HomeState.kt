package com.example.afinal.feature.home.presentation

sealed interface HomeState {
    data class Content(
        val title: String,
        val selectedTab: Int
    ) : HomeState
}