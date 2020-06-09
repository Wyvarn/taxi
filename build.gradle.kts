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

plugins {
    id(Libs.detektPlugin).version(Libs.detektPluginVersion)
}

allprojects {
    apply(plugin = Libs.detektPlugin)

    repositories {
        google()
        jcenter()
    }
}

subprojects {
    val userHome = System.getProperty("user.home")
    val moduleName = this.name

    detekt {
        toolVersion = Libs.detektPluginVersion
        description = "Runs detekt formatter"
        config = files(
            project.rootDir.resolve("config/detekt/detekt-config.yml"),
            project.rootDir.resolve("config/detekt/failfast.yml")
        )
        parallel = true
        input = files("src/main/kotlin", "src/test/kotlin")
        debug = true
        reports {
            xml {
                enabled = false
            }
            html {
                enabled = true
                destination =
                    file(project.rootDir.resolve("build/reports/detekt/$moduleName-report.html"))
            }
        }

        idea {
            path = "$userHome/.idea"
            codeStyleScheme = "$userHome/.idea/idea-code-style.xml"
            inspectionsProfile = "$userHome/.idea/inspect.xml"
            report = "project.projectDir/reports"
            mask = "*.kt"
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
