package com.example.afinal.di

import android.content.Context
import com.example.afinal.MainActivity
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
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