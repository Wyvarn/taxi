object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:3.6.0"

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        const val coreKtx = "androidx.core:core-ktx:1.2.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"

        object Test {
            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
            const val jUnit = "androidx.test.ext:junit:1.1.1"
        }
    }

    object Kotlin {
        private const val version = Versions.Build.kotlinVersion
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
    }

    object Test {
        private const val jupiterVersion = "5.6.2"
        const val jUnitJupiter = "org.junit.jupiter:junit-jupiter:$jupiterVersion"
        const val jUnitJupiterApi = "org.junit.jupiter:junit-jupiter-api:$jupiterVersion"
        const val jUnitJupiterEngine = "org.junit.jupiter:junit-jupiter-engine:$jupiterVersion"
        const val mockk = "io.mockk:mockk:1.10.0"
    }

    object Maps {
        const val playServices = "com.google.android.gms:play-services-maps:17.0.0"
        const val places = "com.google.android.libraries.places:places:2.2.0"
    }
}