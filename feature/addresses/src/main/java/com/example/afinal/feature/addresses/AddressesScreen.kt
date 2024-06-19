package com.example.afinal.feature.addresses

import com.example.afinal.feature.addresses.ui.AddressesFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getAddressesScreen() = FragmentScreen { AddressesFragment.newInstance() }