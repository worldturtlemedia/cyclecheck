package com.worldturtlemedia.cyclecheck.core.ktx

import android.os.Bundle
import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.NavArgsLazy
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.worldturtlemedia.cyclecheck.core.ktx.util.NavigateOptions

@MainThread
inline fun <reified Args : NavArgs> Fragment.navArgs() = NavArgsLazy(Args::class) {
    arguments
        ?: activity?.intent?.extras
        ?: throw IllegalStateException("Fragment $this has null arguments")
}

/**
 * Quick extension to grab a nullable [NavController]
 * ```
 * findNavController()?.navigate(R.id.step2) ?: throw IllegalStateException("No view found!")
 * ```
 */
fun Fragment.findNavController(): NavController? = view?.findNavController()

/**
 * Navigates view a [NavDirections] class
 * ```
 * navigateTo { MainFragmentDirections.showSetupScreen() }
 * ```
 */
fun Fragment.navigateTo(block: () -> NavDirections) {
    block.invoke().let { findNavController()?.navigate(it) }
}

/**
 * Navigates with a pre-built [NavigateOptions]
 * ```
 * val anim = NavOptions.Builder().setEnterAnim(R.anim.nav_default_enter_anim).build()
 * val options = NavigateOptions(null, anim, null)
 *
 * navigateWith(navOptions = options) { MainFragmentDirections.showSetupScreen() }
 * ```
 */
inline fun Fragment.navigateWith(
    navOptions: NavigateOptions = NavigateOptions(),
    block: NavigateOptions.() -> NavDirections
) {
    block.invoke(navOptions).apply {
        val (args, opts, extras) = navOptions
        findNavController()?.navigate(actionId, args ?: arguments, opts, extras)
    }
}

/**
 * Allows you to individually set the [NavigateOptions], see [Fragment.navigateWith]
 * ```
 * val anim = NavOptions.Builder().setEnterAnim(R.anim.nav_default_enter_anim).build()
 * navigateWith(navOptions = anim) { MainFragmentDirections.showSetupScreen() }
 * ```
 */
inline fun Fragment.navigateWith(
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    extras: Navigator.Extras? = null,
    block: NavigateOptions.() -> NavDirections
) {
    navigateWith(NavigateOptions(args, navOptions, extras), block)
}

/**
 * Allows you to individually set the [NavigateOptions], and more easily set the
 * shared element transitions.  See [FragmentNavigatorExtras] and [View.getTransitionName]
 * ```
 * val targetButton = findViewById<AppCompatButton>(R.id.button)
 * navigateWith(sharedElements = targetButton to targetButton.transitionName) {
 *     MainFragmentDirections.showSetupScreen()
 * }
 * ```
 */
inline fun Fragment.navigateWith(
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    vararg sharedElements: Pair<View, String>? = kotlin.emptyArray(),
    block: NavigateOptions.() -> NavDirections
) {
    val elements = sharedElements.filterNotNull().toTypedArray()
    navigateWith(args, navOptions, FragmentNavigatorExtras(*elements), block)
}

/**
 * Helper extension for [Fragment.navigateWith] for when you need to navigate
 * with shared element transitions.  Is a vararg, so you can pass in multiple
 * shared elements.
 * ```
 * val target = findViewById<AppCompatButton>(R.id.button)
 * navigateTo(target to target.transitionName) { MainFragmentDirections.showSetupScreen() }
 * ```
 */
inline fun Fragment.navigateTo(
    vararg sharedElements: Pair<View, String>? = kotlin.emptyArray(),
    block: () -> NavDirections
) {
    navigateWith(sharedElements = *sharedElements, block = { block.invoke() })
}
