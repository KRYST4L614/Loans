package com.example.afinal.di

import kotlin.properties.Delegates

object FragmentDependenciesStore : FragmentDependenciesProvider {

    override var dependencies: FragmentDependencies by Delegates.notNull()
}