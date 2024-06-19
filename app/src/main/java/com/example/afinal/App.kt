package com.example.afinal

import android.app.Application
import com.example.afinal.di.DaggerAppComponent

class App : Application() {
    val appComponent by lazy {
        DaggerAppComponent.builder().context(this).build()
    }
}