import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.jacocoPlugin)
        classpath(kotlin("gradle-plugin", version = Versions.Build.kotlinVersion))
    }
}

plugins {
    id(Libs.detektPlugin).version(Libs.detektPluginVersion)
    jacoco
}

apply(from = "githooks.gradle.kts")

allprojects {
    apply(plugin = Libs.detektPlugin)
    apply(plugin = "jacoco")

    repositories {
        google()
        jcenter()
    }

    jacoco {
        toolVersion = Libs.jacocoPluginVersion
    }
}

subprojects {
    val moduleName = this.name
    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            // set options for log level LIFECYCLE
            events = setOf(
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED
            )
            exceptionFormat = TestExceptionFormat.FULL
            showExceptions = true
            showCauses = true
            showStackTraces = true
        }
        reports {
            html.isEnabled = true
            html.destination =
                file(project.rootDir.resolve("$buildDir/reports/tests"))
        }
        finalizedBy(tasks.getByName("testCoverageReport"))
    }

    val testCoverageReport by tasks.registering(JacocoReport::class) {
        dependsOn.add("test")
        group = "coverage"
        description = "Generate JaCoCo Test report"
        dependsOn.add(tasks.getByName("test"))

        val mainSrcKotlin = "$moduleName/src/main/kotlin"
        val mainSrcJava = "$moduleName/src/main/java"

        // directories to cover
        val coverageSourceDirs = listOf(mainSrcJava, mainSrcKotlin)

        // files to filter out from report
        val fileFilter =
            listOf(
                "**/R.class",
                "**/R$*.class",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                "android/**/*.*",
                "**/*Test*.*"
            )

        val javaClasses = fileTree(
            mapOf(
                "dir" to "$buildDir/intermediates/classes/debug", "excludes" to fileFilter
            )
        )

        val kotlinClasses = fileTree(
            mapOf(
                "dir" to "$buildDir/tmp/kotlin-classes/debug", "excludes" to fileFilter
            )
        )

        classDirectories.setFrom(files(listOf(javaClasses, kotlinClasses)))
        additionalSourceDirs.setFrom(files(coverageSourceDirs))
        sourceDirectories.setFrom(files(coverageSourceDirs))
        executionData.setFrom(
            fileTree(
                mapOf(
                    "dir" to buildDir,
                    "includes" to listOf(
                        "$buildDir/jacoco/testDebugUnitTest.exec",
                        "$buildDir/output/code-coverage/connected/*.ec"
                    )
                )
            )
        )

        reports {
            xml.isEnabled = true
            xml.destination =
                file(project.rootDir.resolve("$buildDir/reports/test-coverage/$moduleName-coverage-report.xml"))
            html.isEnabled = true
            html.destination =
                file(project.rootDir.resolve("$buildDir/reports/test-coverage"))
        }

        finalizedBy(tasks.getByName("testCoverageVerification"))
    }

    val testCoverageVerification by tasks.registering(JacocoCoverageVerification::class) {
        group = "coverage"
        description = "Check Test Coverage Violation"

        dependsOn.add(testCoverageReport)

        violationRules {
            rule {
                limit {
                    minimum = BigDecimal.valueOf(0.2)
                }
            }

            rule {
                enabled = false
                element = "CLASS"
                includes = listOf("org.gradle.*")

                limit {
                    counter = "LINE"
                    value = "TOTALCOUNT"
                    maximum = "0.3".toBigDecimal()
                }
            }
        }
    }

    detekt {
        toolVersion = Libs.detektPluginVersion
        description = "Runs detekt formatter"
        config = files(
            project.rootDir.resolve("config/detekt/detekt.yml"),
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
                    file(project.rootDir.resolve("$buildDir/reports/lint/$moduleName-report.html"))
            }
        }

        idea {
            path = "${project.rootDir}/.idea"
            codeStyleScheme = "${project.rootDir}/.idea/idea-code-style.xml"
            inspectionsProfile = "${project.rootDir}/.idea/inspect.xml"
            report = "$projectDir/build/reports"
            mask = "*.kt"
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
