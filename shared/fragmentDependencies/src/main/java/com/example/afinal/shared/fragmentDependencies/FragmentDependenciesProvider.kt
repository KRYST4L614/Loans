package com.example.afinal.shared.fragmentDependencies

interface FragmentDependenciesProvider {
    val dependencies: FragmentDependencies

    companion object : FragmentDependenciesProvider by FragmentDependenciesStore
}
