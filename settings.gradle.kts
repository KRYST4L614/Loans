pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Final"
include(":app")
include(":component:resources")
include(":feature:auth")
include(":feature:onboarding")
include(":feature:home")
include(":feature:language")
include(":feature:support")
include(":feature:special")
include(":feature:addresses")
include(":shared:fragmentDependencies")
include(":shared:viewModelFactory")
include(":shared:viewPagerAdapter")