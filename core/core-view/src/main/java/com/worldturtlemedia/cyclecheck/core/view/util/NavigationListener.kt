package com.worldturtlemedia.cyclecheck.core.view.util

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.worldturtlemedia.cyclecheck.core.view.BuildConfig
import com.worldturtlemedia.cyclecheck.core.view.util.NavigationListener.Companion.using
import timber.log.Timber

class NavigationListener(private val nav: NavController) {

    @Suppress("MemberVisibilityCanBePrivate")
    fun listen(then: ((NavDestination) -> Unit)? = null) {
        nav.addOnDestinationChangedListener { _, destination, _ ->
            @Suppress("ConstantConditionIf")
            if (BuildConfig.DEBUG) {
                Timber.i("Navigating to ${destination.label ?: destination.id}")
            }
            then?.invoke(destination)
        }
    }

    infix fun with(block: NavDestination.() -> Unit) {
        listen { it.apply(block) }
    }

    infix fun then(then: (destination: NavDestination) -> Unit) {
        listen(then)
    }

    companion object {

        infix fun with(nav: NavController): NavigationListener =
            NavigationListener(nav)

        infix fun using(nav: NavController) = NavigationListener(nav)
    }
}

fun NavController.onDestinationChanged(block: NavDestination.() -> Unit) =
    using(this).with(block)
