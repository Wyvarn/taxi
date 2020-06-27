plugins {
    id("kotlin")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    rootProject.path(":domain")?.let { implementation(it) }

    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.DI.javaxAnnotation)
    implementation(Libs.DI.javaxInject)

    testImplementation(Libs.Test.jUnitJupiter)
    testImplementation(Libs.Test.jUnitJupiterApi)
    testImplementation(Libs.Test.jUnitJupiterEngine)
    testImplementation(Libs.Test.mockk)
}
