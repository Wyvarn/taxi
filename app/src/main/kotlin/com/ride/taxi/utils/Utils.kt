package com.ride.taxi.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

/**
 * @author lusinabrian on 25/06/20.
 * @Notes Utility functions
 */

/**
 * Shows dialog
 * @param context [Context] Context to run utility
 * @param title [String] title of dialog
 * @param message [String] message of dialog
 * @param positiveButtonTxt [String] Positive button text for dialog
 * @param action [Function] Action to be performed when positive button is clicked
 * @param cancelable [Boolean] Whether the dialog is cancelable
 */
@Suppress("LongParameterList")
fun showDialog(
    context: Context,
    title: String,
    message: String,
    positiveButtonTxt: String,
    action: () -> Unit,
    cancelable: Boolean = false
) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setCancelable(cancelable)
        .setPositiveButton(positiveButtonTxt) { _, _ ->
            action()
        }
        .show()
}
