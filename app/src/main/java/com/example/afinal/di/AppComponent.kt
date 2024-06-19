package com.example.afinal.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PresentationModule::class, NavigationModule::class])
interface AppComponent : FragmentDependencies {

    override val viewModelFactory: ViewModelProvider.Factory
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent

    }

}