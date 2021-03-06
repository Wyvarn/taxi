package com.ride.taxi.presenter.maps

import com.ride.taxi.presenter.BasePresenterImpl
import com.ride.taxi.presenter.LAT
import com.ride.taxi.presenter.LNG
import com.ride.taxi.presenter.NEAR_BY_CABS
import com.ride.taxi.presenter.TYPE

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
        // disconnect from WebSocket
    }

    override fun onRequestNearbyCabs(latitude: Double, longitude: Double) {
        val jsonObject = HashMap<String, Any>()
        jsonObject[TYPE] = NEAR_BY_CABS
        jsonObject[LAT] = latitude
        jsonObject[LNG] = longitude
//        webSocket.sendMessage(jsonObject.toString())
    }

    override fun onRequestCab(
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
        // webSocket.sendMessage(jsonObject.toString())
    }

    override fun onConnect() {
        // on web socket connect
    }

    override fun onMessage(data: String) {
        // data
    }

    override fun onDisconnect() {
        // on disconnect
    }

    override fun onError(error: String) {
//        val gson = Gson()
//        val jsonObject = gson.fromJson(error, String::class)
//        when (jsonObject.getString(TYPE)) {
//            ROUTES_NOT_AVAILABLE -> {
//                baseView.showError("Route Not Available: Choose different pickup and drop location")
//            }
//            DIRECTION_API_FAILED -> {
//                baseView.showError(
//                    "Direction API Failed : " + jsonObject.getString(
//                        ERROR
//                    )
//                )
//            }
//        }
    }
}
