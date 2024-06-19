package com.example.afinal

import android.app.Application
import com.example.afinal.di.DaggerAppComponent
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore

class App : Application() {
    val appComponent by lazy {
        DaggerAppComponent.builder().context(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        FragmentDependenciesStore.dependencies =
            appComponent
    }
}