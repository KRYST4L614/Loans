package com.example.afinal.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.MainActivity
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Router
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PresentationModule::class, NavigationModule::class, DataModule::class])
interface AppComponent : FragmentDependencies {
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent

    }

}