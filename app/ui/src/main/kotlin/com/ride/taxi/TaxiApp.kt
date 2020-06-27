package com.ride.taxi

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.google.maps.GeoApiContext
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.ride.taxi.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TaxiApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupGoogleMaps()
        setupAppCenter(this)
        setupDi()
    }

    private fun setupGoogleMaps() {
        val googleMapsKey = getString(R.string.google_maps_key)
        Places.initialize(applicationContext, googleMapsKey)
        GeoApiContext.Builder().apiKey(googleMapsKey).build()
    }

    private fun setupAppCenter(application: Application) {
        AppCenter.start(
            application, getString(R.string.APP_CENTER_TOKEN),
            Analytics::class.java,
            Crashes::class.java
        )
    }

    private fun setupDi() {
        startKoin {
            androidContext(this@TaxiApp)
            modules(presenterModule)
        }
    }
}
