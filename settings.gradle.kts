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
include(":feature:homepage")
include(":feature:menupage")
include(":feature:myloanspage")
include(":feature:loandetails")
include(":feature:requestloan")
include(":feature:rejectloan")
include(":feature:acceptloan")
include(":feature:language")
include(":feature:support")
include(":feature:special")
include(":feature:splash")
include(":feature:addresses")
include(":shared:fragmentdependencies")
include(":shared:viewmodelfactory")
include(":shared:viewpageradapter")
include(":shared:loans")
include(":shared:resourceprovider")
include(":util")