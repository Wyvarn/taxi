import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlin.android.extensions")
    id("kotlin-android")
}

val localProperties = Properties()

try {
    localProperties.load(FileInputStream(rootProject.file("local.properties")))
} catch (e: Exception) {
    localProperties.setProperty("googleMapsApiKey", System.getenv("GOOGLE_MAPS_KEY"))
    localProperties.setProperty("appCenterKey", System.getenv("APP_CENTER_TOKEN"))
}

val keystoreProperties = Properties()
try {
    keystoreProperties.load(FileInputStream(rootProject.file("keystore.properties")))
} catch (e: Exception) {
    keystoreProperties.setProperty("debugKeyAlias", System.getenv("DEBUG_KEY_ALIAS"))
    keystoreProperties.setProperty("debugKeyPass", System.getenv("DEBUG_KEY_PASS"))
    keystoreProperties.setProperty("debugKeyStorePass", System.getenv("DEBUG_KEY_STORE_PASS"))
    keystoreProperties.setProperty("releaseKeyAlias", System.getenv("RELEASE_KEY_ALIAS"))
    keystoreProperties.setProperty("releaseKeyPass", System.getenv("RELEASE_KEY_PASS"))
    keystoreProperties.setProperty("releaseKeyStorePass", System.getenv("RELEASE_KEY_STORE_PASS"))
    keystoreProperties.setProperty("releaseKeyStoreFile", System.getenv("RELEASE_KEY_STORE_FILE"))
}

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
        forEach {
            it.isTestCoverageEnabled = true
            it.resValue("string", "google_maps_key", localProperties["googleMapsApiKey"].toString())
            it.resValue("string", "APP_CENTER_TOKEN", localProperties["appCenterKey"].toString())
        }

        getByName("debug") {
            isMinifyEnabled = false
        }

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
    implementation(Libs.AndroidX.constraintLayout)
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.navigationUiKtx)

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
