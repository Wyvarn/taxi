package com.ride.taxi.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.ride.taxi.R
import kotlin.math.abs
import kotlin.math.atan

/**
 * @author lusinabrian on 25/06/20.
 * @Notes Map Utilities
 */
object MapUtils {

    private const val SCALED_BITMAP_WIDTH = 50
    private const val SCALED_BITMAP_HEIGHT = 100
    private const val BITMAP_HEIGHT = 20
    private const val BITMAP_WIDTH = 20

    /**
     * Get car bitmap
     * @param context [Context] Context to use this utility
     * @return [Bitmap] Bitmap object
     */
    fun getCarBitmap(context: Context): Bitmap {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_car)
        return Bitmap.createScaledBitmap(bitmap, SCALED_BITMAP_WIDTH, SCALED_BITMAP_HEIGHT, false)
    }

    /**
     * Creates a destination bitmap object
     * @return [Bitmap] Bitmap object
     */
    fun getDestinationBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(BITMAP_WIDTH, BITMAP_HEIGHT, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        canvas.drawRect(0F, 0F, BITMAP_WIDTH.toFloat(), BITMAP_HEIGHT.toFloat(), paint)
        return bitmap
    }

    /**
     * Gets rotation
     * @param start [LatLng]
     * @param end [LatLng]
     * @return [Float]
     */
    @Suppress("MagicNumber")
    fun getRotation(start: LatLng, end: LatLng): Float {
        val latDifference: Double = abs(start.latitude - end.latitude)
        val lngDifference: Double = abs(start.longitude - end.longitude)
        var rotation = -1F
        when {
            start.latitude < end.latitude && start.longitude < end.longitude -> {
                rotation = Math.toDegrees(atan(lngDifference / latDifference)).toFloat()
            }
            start.latitude >= end.latitude && start.longitude < end.longitude -> {
                rotation = (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 90).toFloat()
            }
            start.latitude >= end.latitude && start.longitude >= end.longitude -> {
                rotation = (Math.toDegrees(atan(lngDifference / latDifference)) + 180).toFloat()
            }
            start.latitude < end.latitude && start.longitude >= end.longitude -> {
                rotation =
                    (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 270).toFloat()
            }
        }
        return rotation
    }
}
