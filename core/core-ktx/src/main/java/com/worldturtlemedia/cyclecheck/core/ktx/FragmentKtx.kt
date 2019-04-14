package com.worldturtlemedia.cyclecheck.core.ktx

import android.app.Activity
import android.content.Intent
import android.transition.Transition
import android.view.View
import androidx.annotation.TransitionRes
import androidx.fragment.app.Fragment
import androidx.navigation.ActivityNavigator
import androidx.navigation.ActivityNavigator.Extras

fun Fragment.createSharedActivityTransition(sharedView: View?): ActivityNavigator.Extras? {
    return sharedView?.let { view ->
        androidx.core.app.ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            androidx.core.util.Pair.create(view, view.transitionName)
        ).let { Extras.Builder().setActivityOptions(it).build() }
    }
}

inline fun <reified T : Activity> Fragment.startActivity() {
    startActivity(Intent(requireContext(), T::class.java))
}

fun Fragment.launchViewIntent(url: String) = requireContext().launchViewIntent(url)

fun Fragment.updateTransitions(
    enter: Transition? = null,
    exit: Transition? = null,
    sharedEnter: Transition? = null,
    sharedReturn: Transition? = null
) {
    enterTransition = enter ?: enterTransition
    exitTransition = exit ?: exitTransition
    sharedElementEnterTransition = sharedEnter ?: sharedElementEnterTransition
    sharedElementReturnTransition = sharedReturn ?: sharedElementReturnTransition
}

fun Fragment.updateTransitions(
    @TransitionRes enter: Int? = null,
    @TransitionRes exit: Int? = null,
    @TransitionRes sharedEnter: Int? = null,
    @TransitionRes sharedReturn: Int? = null
) = updateTransitions(
    enter = enter?.let { requireContext().inflateTransition(it) },
    exit = exit?.let { requireContext().inflateTransition(it) },
    sharedEnter = sharedEnter?.let { requireContext().inflateTransition(it) },
    sharedReturn = sharedReturn?.let { requireContext().inflateTransition(it) }
)
