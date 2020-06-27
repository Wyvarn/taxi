package com.ride.taxi.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build

/**
 * @author lusinabrian on 28/06/20.
 * @Notes Utility methods for network connectivity
 */

/**
 * Method to check network availability
 * Using ConnectivityManager to check for IsNetwork Connection
 */
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        connectivityManager.registerNetworkCallback(
            object : NetworkRequest() {

            },
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                }
            })
    } else {
        connectivityManager.activeNetworkInfo
    }
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}
