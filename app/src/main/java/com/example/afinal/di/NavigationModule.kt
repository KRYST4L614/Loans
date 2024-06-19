package com.example.afinal.di

import com.example.afinal.feature.auth.AuthRouter
import com.example.afinal.navigation.AuthRouterImpl
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface NavigationModule {
    companion object {
        @Singleton
        @Provides
        fun provideCicerone() = Cicerone.create()

        @Provides
        @Singleton
        fun provideRouter(cicerone: Cicerone<Router>): Router {
            return cicerone.router
        }

        @Provides
        @Singleton
        fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder {
            return cicerone.getNavigatorHolder()
        }
    }

    @Binds
    fun bindAuthRouterImpl(impl: AuthRouterImpl): AuthRouter
}