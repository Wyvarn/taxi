package com.ride.taxi

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.google.maps.GeoApiContext
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes

class TaxiApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupGoogleMaps()
        setupAppCenter(this)
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
}
