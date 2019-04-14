package com.worldturtlemedia.cyclecheck.core.view.bindingadapters

import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.databinding.BindingAdapter
import com.worldturtlemedia.cyclecheck.core.Constants
import com.worldturtlemedia.cyclecheck.core.ktx.updateMargins

@BindingAdapter("android:layout_marginTop")
fun View.updateMarginTop(margin: Float) {
    updateMargins(top = Math.round(margin))
}

@BindingAdapter("android:layout_marginBottom")
fun View.updateMarginBottom(margin: Float) {
    updateMargins(bottom = Math.round(margin))
}

@BindingAdapter("animatedMarginTop")
fun View.animateUpdateMarginTop(finalMargin: Float) {
    val needsToAnimate = Math.round(finalMargin) != marginTop

    if (!needsToAnimate) return

    val start = marginTop
    object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            updateMarginTop(start + ((finalMargin - start) * interpolatedTime))
        }
    }
        .apply { duration = Constants.NAV_HOST_ANIMATION_DURATION }
        .let { startAnimation(it) }
}

@BindingAdapter("animatedMarginBottom")
fun View.animateUpdateMarginBottom(finalMargin: Float) {
    val needsToAnimate = Math.round(finalMargin) != marginBottom

    if (!needsToAnimate) return

    val start = marginBottom
    object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            updateMarginBottom(start + ((finalMargin - start) * interpolatedTime))
        }
    }
        .apply { duration = Constants.NAV_HOST_ANIMATION_DURATION }
        .let { startAnimation(it) }
}

@set:BindingAdapter("visibleOrGone")
var View.visibleOrGone
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

@set:BindingAdapter("visible")
var View.visible
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else INVISIBLE
    }

@set:BindingAdapter("invisible")
var View.invisible
    get() = visibility == INVISIBLE
    set(value) {
        visibility = if (value) INVISIBLE else VISIBLE
    }

@set:BindingAdapter("gone")
var View.gone
    get() = visibility == GONE
    set(value) {
        visibility = if (value) GONE else VISIBLE
    }
