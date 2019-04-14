package com.worldturtlemedia.cyclecheck.core.view.lib

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * An identifier to designate the Arguments for a Fragment using the Navigation component
 *
 * Needs to use the [Parcelize] annotation so the class can be parceled
 *
 * Example:
 *
 * ```
 * @Parcelize
 * data class AmazingFeatureUIArgs(val userID: Int): NavFragmentArgs
 * ```
 */
interface NavFragmentArgs : Parcelable
