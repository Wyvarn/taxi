package com.ride.taxi.presenter

/**
 * @author lusinabrian on 27/06/20.
 * @Notes Base View that will act as a base for any class that is to take the role of the BaseView
 * in the Model-BaseView-Presenter pattern
 */
interface BaseView {
    /**
     * Checks if there is network connected
     * Returns True if the device is connected to a network, false otherwise
     * @return [Boolean]
     */
    val isNetworkConnected: Boolean
}
