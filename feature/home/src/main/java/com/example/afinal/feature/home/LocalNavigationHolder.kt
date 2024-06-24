package com.example.afinal.feature.home

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Cicerone.Companion.create
import com.github.terrakok.cicerone.Router

class LocalCiceroneHolder {
    fun getCicerone(): Cicerone<Router> =
        create()
}