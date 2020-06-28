package com.ride.taxi.ui

import androidx.appcompat.app.AppCompatActivity
import com.ride.taxi.presenter.BaseView
import com.ride.taxi.utils.isNetworkAvailable

/**
 * @author lusinabrian on 28/06/20.
 * @Notes
 */
open class BaseActivity : AppCompatActivity(), BaseView {
    /**
     * Checks if there is network connected
     * Returns True if the device is connected to a network, false otherwise

     * @return [Boolean]
     */
    override val isNetworkConnected: Boolean
        get() = isNetworkAvailable(applicationContext)
}
