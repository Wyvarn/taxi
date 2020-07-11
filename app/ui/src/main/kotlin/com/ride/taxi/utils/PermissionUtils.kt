package com.ride.taxi.utils

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * @author lusinabrian on 25/06/20.
 * @Notes utilities that handle Permissions
 */
object PermissionUtils {

    /**
     * Utility to request access to location of device
     * @param activity [AppCompatActivity] Activity context
     * @param requestId [Int] Request ID
     * @param permissions [Array] array of permissions being requested
     */
    fun requestPermissions(
        activity: AppCompatActivity,
        requestId: Int,
        permissions: Array<String>
    ) {
        ActivityCompat.requestPermissions(activity, permissions, requestId)
    }

    /**
     * Checks if a permission has been granted
     * @param context [Context] Context in which this utility is run
     * @param permission [String] Permission to check permission
     * @return [Boolean] true if the permission has been granted
     */
    fun isPermissionGranted(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context, permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Checks if location has been enabled on device
     * @param context [Context] Context in which this utility is run
     * @return [Boolean] true if the location is enabled, false otherwise
     */
    fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return gpsEnabled || networkEnabled
    }
}
