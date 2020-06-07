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
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        GeoApiContext.Builder().apiKey(getString(R.string.google_maps_key)).build()
        setupAppCenter(this)
    }

    private fun setupAppCenter(application: Application) {
        AppCenter.start(
            application, getString(R.string.app_center_key),
            Analytics::class.java,
            Crashes::class.java
        )
    }
}