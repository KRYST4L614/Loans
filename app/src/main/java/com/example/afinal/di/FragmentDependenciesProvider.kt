package com.example.afinal.di

interface FragmentDependenciesProvider {
    val dependencies: FragmentDependencies

    companion object : FragmentDependenciesProvider by FragmentDependenciesStore
}
