package com.ride.taxi.ui.maps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.ride.taxi.R
import com.ride.taxi.presenter.maps.MapsContract
import com.ride.taxi.ui.BaseActivity
import com.ride.taxi.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_maps.*
import org.koin.android.ext.android.inject

class MapsActivity : BaseActivity(), MapsContract.View, OnMapReadyCallback, View.OnClickListener {
    val mapsPresenter: MapsContract.Presenter<MapsContract.View> by inject()
    private lateinit var googleMap: GoogleMap

    private var pickUpLatLng: LatLng? = null
    private var dropLatLng: LatLng? = null
    private val nearbyCabMarkerList = arrayListOf<Marker>()
    private var destinationMarker: Marker? = null
    private var originMarker: Marker? = null
    private var greyPolyLine: Polyline? = null
    private var blackPolyline: Polyline? = null
    private var previousLatLngFromServer: LatLng? = null
    private var currentLatLngFromServer: LatLng? = null
    private var movingCabMarker: Marker? = null
    private var currentLatLng: LatLng? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 999
        private const val PICKUP_REQUEST_CODE = 1
        private const val DROP_REQUEST_CODE = 2
        private const val CAMERA_ZOOM = 15.5f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mapsPresenter.onAttach(this)
        ViewUtils.enableTransparentStatusBar(window)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onClick(v: View?) {
        when (v) {
            pickUpTextView -> {
                mapsPresenter.launchLocationAutocompleteActivity(PICKUP_REQUEST_CODE)
            }

            dropTextView -> {
                mapsPresenter.launchLocationAutocompleteActivity(DROP_REQUEST_CODE)
            }

            requestCabButton -> {
                statusTextView.visibility = View.VISIBLE
                statusTextView.text = getString(R.string.requesting_your_cab)
                requestCabButton.isEnabled = false
                pickUpTextView.isEnabled = false
                dropTextView.isEnabled = false
                mapsPresenter.requestCab(
                    pickupLatitude = pickUpLatLng?.latitude!!,
                    pickupLongitude = pickUpLatLng?.longitude!!,
                    dropLatitude = dropLatLng?.latitude!!,
                    dropLongitude = dropLatLng?.longitude!!
                )
            }

            nextRideButton -> {
                mapsPresenter.onReset()
            }
        }
    }

    override fun launchLocationAutocompleteActivity(requestCode: Int) {
        val fields: List<Place.Field> =
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .build(this)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICKUP_REQUEST_CODE || requestCode == DROP_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data!!)

                    when (requestCode) {
                        PICKUP_REQUEST_CODE -> {
                            pickUpTextView.text = place.name
                            pickUpLatLng = place.latLng
                            mapsPresenter.showRequestButton()
                        }

                        DROP_REQUEST_CODE -> {
                            dropTextView.text = place.name
                            dropLatLng = place.latLng
                            mapsPresenter.showRequestButton()
                        }
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    Log.d(this::class.simpleName, status.statusMessage!!)
                }
                Activity.RESULT_CANCELED -> {
                    Log.d(this::class.simpleName, "Place Selection Canceled")
                }
            }
        }
    }

    override fun showRequestButton() {
        if (pickUpLatLng !== null && dropLatLng !== null) {
            requestCabButton.visibility = View.VISIBLE
            requestCabButton.isEnabled = true
        }
    }

    override fun resetView() {
        statusTextView.visibility = View.GONE
        nextRideButton.visibility = View.GONE
        nearbyCabMarkerList.forEach { it.remove() }
        nearbyCabMarkerList.clear()
        previousLatLngFromServer = null
        currentLatLngFromServer = null
        if (currentLatLng != null) {
            mapsPresenter.onMoveCamera(currentLatLng?.longitude!!, currentLatLng?.latitude!!)
            mapsPresenter.onAnimateCamera(currentLatLng?.longitude!!, currentLatLng?.latitude!!)
            mapsPresenter.onSetCurrentLocationAsPickup()
            mapsPresenter.onRequestNearbyCabs(currentLatLng?.latitude!!, currentLatLng?.longitude!!)
        } else {
            pickUpTextView.text = ""
        }
        pickUpTextView.isEnabled = true
        dropTextView.isEnabled = true
        dropTextView.text = ""
        movingCabMarker?.remove()
        greyPolyLine?.remove()
        blackPolyline?.remove()
        originMarker?.remove()
        destinationMarker?.remove()
        dropLatLng = null
        greyPolyLine = null
        blackPolyline = null
        originMarker = null
        destinationMarker = null
        movingCabMarker = null
    }

    override fun moveCameraView(longitude: Double, latitude: Double) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude, longitude)))
    }

    override fun animateCameraView(longitude: Double, latitude: Double) {
        val cameraPosition =
            CameraPosition.Builder().target(LatLng(latitude, longitude)).zoom(CAMERA_ZOOM).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun setCurrentLocationAsPickup() {
        pickUpLatLng = currentLatLng
        pickUpTextView.text = getString(R.string.current_location)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
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
