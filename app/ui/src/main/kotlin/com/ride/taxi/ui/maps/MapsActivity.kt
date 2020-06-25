package com.ride.taxi.ui.maps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.ride.taxi.R

class MapsActivity : AppCompatActivity(), MapsView, OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
    }

    override fun showNearbyCabs(latLngList: List<LatLng>) {
    }

    override fun informCabBooked() {
    }

    override fun showPath(latLngList: List<LatLng>) {
    }

    override fun updateCabLocation(latLng: LatLng) {
    }

    override fun informCabIsArriving() {
    }

    override fun informCabArrived() {
    }

    override fun informTripStart() {
    }

    override fun informTripEnd() {
    }

    override fun showRoutesNotAvailableError() {
    }

    override fun showDirectionApiFailedError(error: String) {
    }

    override fun onMapReady(googleMap: GoogleMap?) {
    }
}
