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

rootProject.name = "Rsa Eval Task"
include(":app")
include(":app:implementation")
include(":core:icons:api")
include(":feature:permissions:view:implementation")
include(":feature:connect:domain:api")
include(":feature:connect:domain:implementation")
include(":feature:connect:view:implementation")
include(":feature:scan:domain:api")
include(":feature:scan:domain:implementation")
include(":feature:scan:view:implementation")
