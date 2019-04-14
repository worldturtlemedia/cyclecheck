package com.worldturtlemedia.cyclecheck.core.ktx

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.ConnectivityManager
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.annotation.TransitionRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.updateBounds
import com.github.ajalt.timberkt.e

fun Context.color(@ColorRes color: Int): Int = ContextCompat.getColor(this, color)

fun Context.string(@StringRes id: Int, vararg formatArgs: Any):
    String = resources.getString(id, *formatArgs)

fun Context.int(@IntegerRes int: Int): Int = resources.getInteger(int)

fun Context.dimen(@DimenRes dimen: Int): Float = resources.getDimension(dimen)

fun Context.inflateTransition(@TransitionRes id: Int): Transition =
    TransitionInflater.from(this).inflateTransition(id)

val Context.inputManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun Context.drawableToBitmap(@DrawableRes drawable: Int): Bitmap {
    return BitmapFactory.decodeResource(this.resources, drawable)
}

fun Context.vectorDrawableToBitmap(@DrawableRes drawableId: Int): Bitmap {
    return ContextCompat.getDrawable(this, drawableId)?.run {
        val bitmap = Bitmap.createBitmap(
            intrinsicWidth,
            intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)

        updateBounds(right = canvas.width, bottom = canvas.height)
        draw(canvas)

        bitmap
    } ?: throw IllegalArgumentException("Could not find drawable with id $drawableId")
}

fun Context.launchViewIntent(@StringRes urlRes: Int): Boolean = launchViewIntent(string(urlRes))

fun Context.launchViewIntent(url: String) = try {
    url.toURI()?.let { viewIntent(it).launch(this) }
        ?: throw IllegalArgumentException("Not a valid URI: $url")
    true
} catch (error: Throwable) {
    e(error) { "Unable to launch view intent" }
    false
}

@SuppressLint("MissingPermission")
fun Context.hasInternet(): Boolean =
    (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)
        ?.activeNetworkInfo
        ?.isConnected
        ?: false

fun Context.hasNoInternet(): Boolean = !hasInternet()
