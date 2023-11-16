pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                username = "mapbox"
                password = "sk.eyJ1IjoiYmFnaGV0dGkiLCJhIjoiY2xwMWZiYzBsMGh0ZjJrczE3YnhrN3lrcyJ9.j-cfePMLZMjz3G-_YX2ImA"
            }
        }
    }
}


rootProject.name = "Transitapp"
include(":app")
 