package com.ride.taxi.ui.maps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.ride.taxi.R
import com.ride.taxi.presenter.maps.MapsContract
import org.koin.android.ext.android.inject

class MapsActivity : BaseActivity(), MapsContract.View, OnMapReadyCallback {
    val mapsPresenter: MapsContract.Presenter<MapsContract.View> by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mapsPresenter.onAttach(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
    }

    override fun showNearbyCabs(latLngList: List<Pair<Double, Double>>) {
    }

    override fun informCabBooked() {
    }

    override fun showPath(latLngList: List<Pair<Double, Double>>) {
    }

    override fun updateCabLocation(latitude: Double, longitude: Double) {
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
}
