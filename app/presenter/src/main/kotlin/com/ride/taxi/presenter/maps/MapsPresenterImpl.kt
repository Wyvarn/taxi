package com.ride.taxi.presenter.maps

/**
 * @author lusinabrian on 27/06/20.
 * @Notes Presenter Implementation
 */
class MapsPresenterImpl : MapsContract.Presenter {

    override fun onAttach() {
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
