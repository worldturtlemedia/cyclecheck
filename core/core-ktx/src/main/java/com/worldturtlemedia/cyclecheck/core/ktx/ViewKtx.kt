package com.worldturtlemedia.cyclecheck.core.ktx

import android.animation.TimeInterpolator
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.annotation.Px
import androidx.annotation.StringRes
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

fun View.setMarginTop(margin: Int) {
    updateMargins(top = margin)
}

fun View.updateMargins(
    @Px left: Int = marginLeft,
    @Px top: Int = marginTop,
    @Px right: Int = marginRight,
    @Px bottom: Int = marginBottom
) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        leftMargin = left
        topMargin = top
        rightMargin = right
        bottomMargin = bottom
    }?.let { layoutParams = it }
}

fun View.onClick(listener: (View) -> Unit) {
    this.setOnClickListener(listener)
}

fun <T : View> View.onClickTyped(listener: (T) -> Unit) {
    @Suppress("UNCHECKED_CAST")
    this.setOnClickListener {
        (this as? T)?.let { view -> listener(view) }
    }
}

fun View.showMessage(@StringRes resID: Int, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, resID, duration).show()
}

fun View.showMessage(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
}

fun View.hideKeyboard() {
    try {
        context.inputManager.hideSoftInputFromWindow(this.windowToken, 0)
    } catch (error: Error) {
        Timber.w("Unable to hide keyboard, keyboard might not be open")
    }
}

fun View.showKeyboard() {
    try {
        context.inputManager.showSoftInput(this, 0)
    } catch (error: Error) {
        Timber.w("Unable to hide keyboard, keyboard might not be open")
    }
}

fun View.requestFocusWithKeyboard() {
    requestFocus()
    showKeyboard()
}

fun View.clearFocusAndHideKeyboard() {
    clearFocus()
    hideKeyboard()
}

fun View.onLayoutChange(block: (View) -> Unit) {
    addOnLayoutChangeListener { v, _, _, _, _, _, _, _, _ ->
        block(v)
    }
}

fun View.animateOffscreenBottomDown(
    duration: Long? = null,
    defaultHeight: Int = height,
    factor: Float = 2f,
    distance: Float = 20f
) {
    val to = if (height == 0) defaultHeight else height
    animateYAccelerate(to + distance, duration, factor)
}

fun View.animateOnscreenBottomUp(duration: Long? = null, factor: Float = 2f) =
    animateYDecelerate(0f, duration, factor)

fun View.animateYDecelerate(to: Float, duration: Long? = null, factor: Float = 2f) =
    animateY(to, DecelerateInterpolator(factor), duration)

fun View.animateYAccelerate(to: Float, duration: Long? = null, factor: Float = 2f) =
    animateY(to, AccelerateInterpolator(factor), duration)

fun View.animateYtoZero(interpolator: TimeInterpolator? = null, duration: Long? = null) {
    animateY(0f, interpolator, duration)
}

fun View.animateY(to: Float, interpolator: TimeInterpolator? = null, duration: Long? = null) {
    animate(interpolator, duration) { translationY(to) }
}

fun View.animateXtoZero(interpolator: TimeInterpolator? = null, duration: Long? = null) {
    animateX(0f, interpolator, duration)
}

fun View.animateX(to: Float = 0f, interpolator: TimeInterpolator? = null, duration: Long? = null) {
    animate(interpolator, duration) { translationX(to) }
}

// TODO - Have this return the animator so that we can stop/cancel/cleanup
private fun View.animate(
    interpolator: TimeInterpolator? = null,
    duration: Long?,
    block: ViewPropertyAnimator.() -> Unit
) {
    animate()
        .setInterpolator(interpolator)
        .apply {
            if (duration != null) setDuration(duration)
            this.apply(block)
        }
        .start()
}
