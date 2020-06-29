package com.ride.taxi.ui.maps

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.ride.taxi.R
import com.ride.taxi.presenter.maps.MapsContract
import com.ride.taxi.ui.BaseActivity
import com.ride.taxi.utils.AnimationUtils
import com.ride.taxi.utils.PermissionUtils
import com.ride.taxi.utils.ViewUtils
import com.ride.taxi.utils.showAlertDialog
import kotlinx.android.synthetic.main.activity_maps.dropTextView
import kotlinx.android.synthetic.main.activity_maps.nextRideButton
import kotlinx.android.synthetic.main.activity_maps.pickUpTextView
import kotlinx.android.synthetic.main.activity_maps.requestCabButton
import kotlinx.android.synthetic.main.activity_maps.statusTextView
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

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var locationCallback: LocationCallback

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
        if (currentLatLng == null) {
            when {
                PermissionUtils.isPermissionGranted(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {
                    when {
                        PermissionUtils.isLocationEnabled(this) -> {
                            mapsPresenter.onSetupLocationListener()
                        }
                        else -> {
                            showAlertDialog(
                                this,
                                title = getString(R.string.enable_gps),
                                message = getString(R.string.required_for_this_app),
                                positiveButtonTxt = getString(R.string.enable_now),
                                action = { mapsPresenter.onOpenLocationSettingsActivity() }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        mapsPresenter.onDetach()
        fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
        super.onDestroy()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when {
                        PermissionUtils.isLocationEnabled(this) -> {
                            mapsPresenter.onSetupLocationListener()
                        }
                        else -> {
                            showAlertDialog(
                                this,
                                title = getString(R.string.enable_gps),
                                message = getString(R.string.required_for_this_app),
                                positiveButtonTxt = getString(R.string.enable_now),
                                action = { mapsPresenter.onOpenLocationSettingsActivity() }
                            )
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.location_permission_not_granted),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
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
                mapsPresenter.onRequestCab(
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

    override fun openLocationSettingsActivity() {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    @SuppressLint("MissingPermission")
    override fun setupLocationListener() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        // for getting the current location update after every 2 seconds
        @Suppress("MagicNumber")
        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                if (currentLatLng == null) {
                    for (location in locationResult.locations) {
                        if (currentLatLng == null) {
                            currentLatLng = LatLng(location.latitude, location.longitude)
                            mapsPresenter.onSetCurrentLocationAsPickup()
                            mapsPresenter.onEnableMyLocationOnMap()
                            mapsPresenter.onMoveCamera(
                                currentLatLng?.longitude!!,
                                currentLatLng?.latitude!!
                            )
                            mapsPresenter.onAnimateCamera(
                                currentLatLng?.longitude!!,
                                currentLatLng?.latitude!!
                            )
                            mapsPresenter.onRequestNearbyCabs(
                                currentLatLng!!.latitude,
                                currentLatLng?.longitude!!
                            )
                        }
                    }
                }
                // Few more things we can do here:
                // For example: Update the location of user on server
            }
        }
        if (!PermissionUtils.isPermissionGranted(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
            !PermissionUtils.isPermissionGranted(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        fusedLocationProviderClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    @SuppressLint("MissingPermission")
    override fun enableMyLocationOnMap() {
        @Suppress("MagicNumber")
        googleMap.setPadding(0, ViewUtils.dpToPx(48f), 0, 0)
        googleMap.isMyLocationEnabled = !(!PermissionUtils.isPermissionGranted(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) &&
                !PermissionUtils.isPermissionGranted(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
    }

    override fun showNearbyCabs(latLngList: List<Pair<Double, Double>>) {
        nearbyCabMarkerList.clear()
        for (latLng in latLngList) {
            val nearbyCabMarker =
                addCarMarkerAndGet(googleMap, this, LatLng(latLng.first, latLng.second))
            nearbyCabMarkerList.add(nearbyCabMarker)
        }
    }

    override fun informCabBooked() {
        nearbyCabMarkerList.forEach { it.remove() }
        nearbyCabMarkerList.clear()
        requestCabButton.visibility = View.GONE
        statusTextView.text = getString(R.string.your_cab_is_booked)
    }

    override fun showPath(latLngList: List<Pair<Double, Double>>) {
        val builder = LatLngBounds.Builder()
        for (latLng in latLngList) {
            builder.include(LatLng(latLng.first, latLng.second))
        }
        val bounds = builder.build()
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 2))
        val polylineOptions = PolylineOptions()
        polylineOptions.color(Color.GRAY)
        polylineOptions.width(5f)
        polylineOptions.addAll(latLngList)
        greyPolyLine = googleMap.addPolyline(polylineOptions)

        val blackPolylineOptions = PolylineOptions()
        blackPolylineOptions.width(5f)
        blackPolylineOptions.color(Color.BLACK)
        blackPolyline = googleMap.addPolyline(blackPolylineOptions)

        originMarker = addOriginDestinationMarkerAndGet(googleMap,  latLngList[0])
        originMarker?.setAnchor(0.5f, 0.5f)
        destinationMarker = addOriginDestinationMarkerAndGet(googleMap, latLngList[latLngList.size - 1])
        destinationMarker?.setAnchor(0.5f, 0.5f)

        val polylineAnimator = AnimationUtils.polyLineAnimator()
        polylineAnimator.addUpdateListener { valueAnimator ->
            val percentValue = (valueAnimator.animatedValue as Int)
            val index = (greyPolyLine?.points!!.size * (percentValue / 100.0f)).toInt()
            blackPolyline?.points = greyPolyLine?.points!!.subList(0, index)
        }
        polylineAnimator.start()
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

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        mapsPresenter.onReset()
    }
}
