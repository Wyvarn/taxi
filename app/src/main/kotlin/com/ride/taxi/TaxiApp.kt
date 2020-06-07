package com.ride.taxi

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.google.maps.GeoApiContext

class TaxiApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        GeoApiContext.Builder().apiKey(getString(R.string.google_maps_key)).build()
    }
}