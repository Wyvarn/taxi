plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android-extensions")
}

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))

val keyStoreProperties = Properties()
keyStoreProperties.load(FileInputStream(rootProject.file("keystore.properties")))

android {
    compileSdkVersion(Versions.Build.compileSdk)
    buildToolsVersion(Versions.Build.buildTools)

    defaultConfig {
        applicationId("com.ride.taxi")
        minSdkVersion(Versions.Build.minSdk)
        targetSdkVersion(Versions.Build.targetSdk)
        versionCode(1)
        versionName("1.0")

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = keystoreProperties["debugKeyAlias"].toString()
        }

        create("release") {
            keyAlias = keystoreProperites["releaseKeyAlias"].toString()
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            isMinifyEnabled = false
            signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Libs.Kotlin.stdlib)

    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.constraintLayout)
    implementation(Libs.AndroidX.coreKtx)

    implementation(Libs.Maps.playServices)
    implementation(Libs.Maps.places)

    testImplementation(Libs.Test.jUnitJupiter)
    testImplementation(Libs.Test.jUnitJupiterApi)
    testImplementation(Libs.Test.jUnitJupiterEngine)
    testImplementation(Libs.Test.mockk)

    androidTestImplementation(Libs.AndroidX.Test.espressoCore)
    androidTestImplementation(Libs.AndroidX.Test.junit)
}
