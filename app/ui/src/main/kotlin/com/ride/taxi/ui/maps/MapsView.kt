package com.ride.taxi.ui.maps

/**
 * @author lusinabrian on 11/07/20.
 * @Notes View layer
 */
interface MapsView {

    /**
     * setup location listener
     */
    fun setupLocationListener()

    /**
     * Resets view
     */
    fun resetView()

    /**
     * Sets the Pickup location
     */
    fun showRequestButton()

    /**
     * Launchers Auto Complete Activity given the request code
     * @param requestCode [Int]
     */
    fun launchLocationAutocompleteActivity(requestCode: Int)

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

    /**
     * Enables user's location on map
     */
    fun enableMyLocationOnMap()

    /**
     * Instructs view to open location settings
     */
    fun openLocationSettingsActivity()
}
