import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlin.android.extensions")
}

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))

val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(rootProject.file("keystore.properties")))

android {
    compileSdkVersion(Versions.Build.compileSdk)
    buildToolsVersion(Versions.Build.buildTools)

    defaultConfig {
        applicationId = "com.ride.taxi"
        minSdkVersion(Versions.Build.minSdk)
        targetSdkVersion(Versions.Build.targetSdk)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = keystoreProperties["debugKeyAlias"].toString()
        }

        create("release") {
            keyAlias = keystoreProperties["releaseKeyAlias"].toString()
            keyPassword = keystoreProperties["releaseKeyPass"].toString()
            storePassword = keystoreProperties["releaseKeyStorePass"].toString()
            storeFile = rootProject.file(keystoreProperties["releaseKeyStoreFile"].toString())
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            resValue("string", "google_maps_key", localProperties["googleMapsApiKey"].toString())
            resValue("string", "app_center_key", localProperties["appCenterKey"].toString())
        }

        getByName("release") {
            isMinifyEnabled = false
            resValue("string", "google_maps_key", localProperties["googleMapsApiKey"].toString())
            resValue("string", "app_center_key", localProperties["appCenterKey"].toString())
            signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Libs.Kotlin.stdlib)

    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.constraintLayout)
    implementation(Libs.AndroidX.coreKtx)

    implementation(Libs.Google.playServicesMaps)
    implementation(Libs.Google.places)
    implementation(Libs.Google.maps)

    implementation(Libs.AppCenter.analytics)
    implementation(Libs.AppCenter.crashes)

    testImplementation(Libs.Test.jUnitJupiter)
    testImplementation(Libs.Test.jUnitJupiterApi)
    testImplementation(Libs.Test.jUnitJupiterEngine)
    testImplementation(Libs.Test.mockk)

    androidTestImplementation(Libs.AndroidX.Test.espressoCore)
    androidTestImplementation(Libs.AndroidX.Test.jUnit)
}
