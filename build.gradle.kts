buildscript {

    repositories {
        google()
        jcenter()
        maven(url = "https://maven.fabric.io/public")
    }

    dependencies {
        classpath(Plugins.plugin_gradleTools)
        classpath(Plugins.plugin_gradleKotlin)
        classpath(Plugins.plugin_safeArgs)
    }
}

allprojects {

    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}