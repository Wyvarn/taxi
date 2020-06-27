package com.ride.taxi.presenter.maps

import com.ride.taxi.presenter.BasePresenterImpl

/**
 * @author lusinabrian on 27/06/20.
 * @Notes Presenter Implementation
 */
class MapsPresenterImpl<V : MapsContract.View> : MapsContract.Presenter<V>, BasePresenterImpl<V>() {

    override fun onAttach(view: V) {
        super.onAttach(view)
        // connect to web socket
    }

    override fun onDetach() {
    }

    override fun requestNearbyCabs(latitude: Double, longitude: Double) {}

    override fun requestCab(
        pickupLatitude: Double,
        pickupLongitude: Double,
        dropLatitude: Double,
        dropLongitude: Double
    ) {
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
