package com.example.afinal.feature.menupage

import com.example.afinal.feature.menupage.ui.MenuPageFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getMenuPageScreen() = FragmentScreen { MenuPageFragment.newInstance() }