package com.ride.taxi.ui.maps

import com.google.android.gms.maps.model.LatLng

/**
 * @author lusinabrian on 25/06/20.
 * @Notes Maps View
 */
interface MapsView {

    fun showNearbyCabs(latLngList: List<LatLng>)

    fun informCabBooked()

    fun showPath(latLngList: List<LatLng>)

    fun updateCabLocation(latLng: LatLng)

    fun informCabIsArriving()

    fun informCabArrived()

    fun informTripStart()

    fun informTripEnd()

    fun showRoutesNotAvailableError()

    fun showDirectionApiFailedError(error: String)
}
