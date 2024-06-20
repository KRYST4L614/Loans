package com.example.afinal.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.MainActivity
import com.example.afinal.feature.auth.data.AuthApiService
import com.example.afinal.feature.auth.data.AuthRepositoryImpl
import com.example.afinal.feature.auth.domain.AuthRepository
import com.example.afinal.shared.fragmentDependencies.FragmentDependencies
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [PresentationModule::class, NavigationModule::class, DataModule::class])
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