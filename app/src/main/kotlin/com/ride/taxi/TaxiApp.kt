package com.ride.taxi

import android.app.Application
import com.google.android.libraries.places.api.Places
class TaxiApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
    }
}