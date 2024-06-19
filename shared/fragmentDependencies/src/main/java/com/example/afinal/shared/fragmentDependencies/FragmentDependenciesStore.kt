package com.example.afinal.shared.fragmentDependencies

import kotlin.properties.Delegates

object FragmentDependenciesStore : FragmentDependenciesProvider {

    override var dependencies: FragmentDependencies by Delegates.notNull()
}