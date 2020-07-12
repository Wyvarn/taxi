plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.kotlin.android.extensions")
}

android {
    compileSdkVersion(Versions.Build.compileSdk)
    buildToolsVersion(Versions.Build.buildTools)

    defaultConfig {
        minSdkVersion(Versions.Build.minSdk)
        targetSdkVersion(Versions.Build.targetSdk)
        versionCode = 1
        versionName = "1.0"
        consumerProguardFiles("consumer-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            // Enable this, if you CI provider does not cater to signing the application code
            // signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.Kotlin.stdlib)

    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.coreKtx)

    // Google Maps Services (needed for directions)
    api(Libs.Google.playServicesMaps)
    api(Libs.Google.places)
    api(Libs.Google.maps)

    testImplementation(Libs.Test.jUnitJupiter)
    testImplementation(Libs.Test.jUnitJupiterApi)
    testImplementation(Libs.Test.jUnitJupiterEngine)
    testImplementation(Libs.Test.mockk)
}
