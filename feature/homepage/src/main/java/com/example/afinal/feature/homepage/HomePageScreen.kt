package com.example.afinal.feature.homepage

import com.example.afinal.feature.homepage.ui.HomePageFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getHomePageScreen() = FragmentScreen{ HomePageFragment.newInstance() }