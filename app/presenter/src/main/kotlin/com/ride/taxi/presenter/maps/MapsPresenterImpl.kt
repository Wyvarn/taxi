package com.ride.taxi.presenter.maps

import com.ride.taxi.presenter.*

/**
 * @author lusinabrian on 27/06/20.
 * @Notes Presenter Implementation
 */
class MapsPresenterImpl<V : MapsContract.View> : MapsContract.Presenter<V>, BasePresenterImpl<V>() {

    override fun onAttach(view: V) {
        super.onAttach(view)
        // connect to web socket
//        webSocket = networkService.createWebSocket(this)
//        webSocket.connect()
    }

    override fun onDetach() {
    }

    override fun launchLocationAutocompleteActivity(requestCode: Int) {
        baseView.launchLocationAutocompleteActivity(requestCode)
    }

    override fun showRequestButton() {
        baseView.showRequestButton()
    }

    override fun onRequestNearbyCabs(latitude: Double, longitude: Double) {
        val jsonObject = HashMap<String, Any>()
        jsonObject[TYPE] = NEAR_BY_CABS
        jsonObject[LAT] = latitude
        jsonObject[LNG] = longitude
//        webSocket.sendMessage(jsonObject.toString())
    }

    override fun requestCab(
        pickupLatitude: Double,
        pickupLongitude: Double,
        dropLatitude: Double,
        dropLongitude: Double
    ) {
        val jsonObject = HashMap<String, Any>()
        jsonObject["type"] = "requestCab"
        jsonObject["pickUpLat"] = pickupLatitude
        jsonObject["pickUpLng"] = pickupLongitude
        jsonObject["dropLat"] = dropLatitude
        jsonObject["dropLng"] = dropLongitude
        // TODO: send message to web socket
//        webSocket.sendMessage(jsonObject.toString())
    }

    override fun onReset() {
        baseView.resetView()
    }

    override fun onMoveCamera(longitude: Double, latitude: Double) {
        baseView.moveCameraView(longitude, latitude)
    }

    override fun onAnimateCamera(longitude: Double, latitude: Double) {
        baseView.animateCameraView(longitude, latitude)
    }

    override fun onSetCurrentLocationAsPickup() {
        baseView.setCurrentLocationAsPickup()
    }

    override fun onConnect() {
    }

    override fun onMessage(data: String) {
    }

    override fun onDisconnect() {
    }

    override fun onError(error: String) {
    }
}
