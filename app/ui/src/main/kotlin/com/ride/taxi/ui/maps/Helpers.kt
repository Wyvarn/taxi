package com.ride.taxi.ui.maps

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.ride.taxi.utils.MapUtils

/**
 * @author lusinabrian on 29/06/20.
 * @Notes Helper functions for
 */

fun addCarMarkerAndGet(googleMap: GoogleMap, context: Context, latLng: LatLng): Marker {
    val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(MapUtils.getCarBitmap(context))
    return googleMap.addMarker(MarkerOptions().position(latLng).flat(true).icon(bitmapDescriptor))
}

fun addOriginDestinationMarkerAndGet(googleMap: GoogleMap, latLng: LatLng): Marker {
    val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(MapUtils.getDestinationBitmap())
    return googleMap.addMarker(MarkerOptions().position(latLng).flat(true).icon(bitmapDescriptor))
}
