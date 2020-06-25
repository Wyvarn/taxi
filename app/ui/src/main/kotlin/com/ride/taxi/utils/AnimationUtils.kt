package com.ride.taxi.utils

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator

/**
 * @author lusinabrian on 25/06/20.
 * @Notes Animation Utilities
 */
object AnimationUtils {

    private const val POLYLINE_ANIMATOR_DURATION = 2000L
    private const val CAB_ANIMATOR_DURATION = 3000L

    fun polyLineAnimator(): ValueAnimator {
        @Suppress("MagicNumber")
        val valueAnimator = ValueAnimator.ofInt(0, 100)
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = POLYLINE_ANIMATOR_DURATION
        return valueAnimator
    }

    fun cabAnimator(): ValueAnimator {
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = CAB_ANIMATOR_DURATION
        valueAnimator.interpolator = LinearInterpolator()
        return valueAnimator
    }
}
