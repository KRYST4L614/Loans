package com.exapmle.afinal.feature.language

import com.exapmle.afinal.feature.language.ui.LanguageFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getLanguageScreen() = FragmentScreen { LanguageFragment.newInstance() }