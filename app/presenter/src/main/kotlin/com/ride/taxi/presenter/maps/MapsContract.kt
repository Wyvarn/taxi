package com.ride.taxi.presenter.maps

import com.ride.taxi.presenter.BasePresenter
import com.ride.taxi.presenter.BaseView

/**
 * @author lusinabrian on 27/06/20.
 * @Notes Defines a contract of operations between the Presenter and View
 */
interface MapsContract {

    interface View : BaseView {
        /**
         * Launchers Auto Complete Activity given the request code
         * @param requestCode [Int]
         */
        fun launchLocationAutocompleteActivity(requestCode: Int)

        /**
         * Sets the Pickup location
         */
        fun showRequestButton()

        /**
         * Resets view
         */
        fun resetView()

        /**
         * Moves camera to new longitude & latitude
         * @param longitude [Double]
         * @param latitude [Double]
         */
        fun moveCameraView(longitude: Double, latitude: Double)

        /**
         * Animates camera to new longitude & latitude
         * @param longitude [Double]
         * @param latitude [Double]
         */
        fun animateCameraView(longitude: Double, latitude: Double)

        /**
         * Sets the current location as pickup
         */
        fun setCurrentLocationAsPickup()

        fun showNearbyCabs(latLngList: List<Pair<Double, Double>>)

        fun informCabBooked()

        fun showPath(latLngList: List<Pair<Double, Double>>)

        fun updateCabLocation(latitude: Double, longitude: Double)

        fun informCabIsArriving()

        fun informCabArrived()

        fun informTripStart()

        fun informTripEnd()

        fun showRoutesNotAvailableError()

        fun showDirectionApiFailedError(error: String)
    }

    interface Presenter<V : View> : BasePresenter<V> {

        /**
         * Launches Auto Complete Activity
         * @param requestCode [Int]
         */
        fun launchLocationAutocompleteActivity(requestCode: Int)

        /**
         * Sets visibility of request button
         */
        fun showRequestButton()

        /**
         * Resets the view
         */
        fun onReset()

        /**
         * Requests for nearby cabs
         * @param latitude [Double]
         * @param longitude [Double]
         */
        fun onRequestNearbyCabs(latitude: Double, longitude: Double)

        /**
         * Requests for a cab from pickup location to drop-off location
         * Uses longitude & latitudes set
         * @param pickupLatitude Pickup Latitude location
         * @param pickupLongitude Pickup longitude location
         * @param dropLatitude Drop-off latitude
         * @param dropLongitude Drop-off Longitude
         */
        fun requestCab(
            pickupLatitude: Double,
            pickupLongitude: Double,
            dropLatitude: Double,
            dropLongitude: Double
        )

        /**
         * Instructs view to move camera to new longitude & latitude
         * @param longitude [Double]
         * @param latitude [Double]
         */
        fun onMoveCamera(longitude: Double, latitude: Double)

        /**
         * Instructs view to animate camera to new longitude & latitude
         * @param longitude [Double]
         * @param latitude [Double]
         */
        fun onAnimateCamera(longitude: Double, latitude: Double)

        /**
         * Sets the current location as pickup
         */
        fun onSetCurrentLocationAsPickup()

        /**
         * Handles connection events from WebSocket. Clients can now open communication to WebSocket
         */
        fun onConnect()

        /**
         * When a message is received, handles message events as received from WebSocket
         * @param data [String] data as received from Web Socket
         */
        fun onMessage(data: String)

        /**
         * Disconnect event as received from Web Socket, signalling to clients that the connection
         * is no longer available
         */
        fun onDisconnect()

        /**
         * On Error events, clients should display to the user what transpired
         * @param error [String] Error message
         */
        fun onError(error: String)
    }
}
