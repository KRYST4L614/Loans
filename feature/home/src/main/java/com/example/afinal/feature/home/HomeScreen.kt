package com.example.afinal.feature.home

import com.example.afinal.feature.home.ui.HomeFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getHomeScreen() = FragmentScreen { HomeFragment.newInstance() }