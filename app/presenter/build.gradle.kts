plugins {
    id("kotlin")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":domain"))
    implementation(project(":simulator"))

    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.DI.javaxAnnotation)
    implementation(Libs.DI.javaxInject)
    implementation(Libs.Utils.gson)

    testImplementation(Libs.Test.jUnitJupiter)
    testImplementation(Libs.Test.jUnitJupiterApi)
    testImplementation(Libs.Test.jUnitJupiterEngine)
    testImplementation(Libs.Test.mockk)
}
