object Libs {

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        const val coreKtx = "androidx.core:core-ktx:1.2.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:2.1.0"

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

    object Google {
        const val playServicesMaps = "com.google.android.gms:play-services-maps:17.0.0"
        const val places = "com.google.android.libraries.places:places:2.2.0"
        const val maps = "com.google.maps:google-maps-services:0.2.9"
    }

    object AppCenter {
        private const val appCenterSdkVersion = "3.2.1"
        const val analytics = "com.microsoft.appcenter:appcenter-analytics:$appCenterSdkVersion"
        const val crashes = "com.microsoft.appcenter:appcenter-crashes:$appCenterSdkVersion"
    }

    object DI {
        const val javaxInject = "javax.inject:javax.inject:1"
        const val javaxAnnotation = "javax.annotation:jsr250-api:1.0"
        private const val koinVersion = "2.1.5"
        const val koinAndroid = "org.koin:koin-android:$koinVersion"
        const val koinAndroidXScope = "org.koin:koin-androidx-scope:$koinVersion"
        const val koinAndroidXViewModel = "org.koin:koin-androidx-viewmodel:$koinVersion"
    }

    object Utils {
        const val gson = "com.google.code.gson:gson:2.8.6"
    }
}
