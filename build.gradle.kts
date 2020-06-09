buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath(Libs.androidGradlePlugin)
        classpath(kotlin("gradle-plugin", version = Versions.Build.kotlinVersion))
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
