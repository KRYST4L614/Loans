package com.example.afinal.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.MainViewModel
import com.example.afinal.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}