package com.worldturtlemedia.cyclecheck.core.ktx.util

import android.content.Context
import com.worldturtlemedia.cyclecheck.core.ktx.launchViewIntent

fun openRateApp(context: Context): Boolean {
    if (openRateInAppStore(context)) return true

    val url = "https://play.google.com/store/apps/details?id=${context.packageName}"
    return context.launchViewIntent(url)
}

private fun openRateInAppStore(context: Context) =
    context.launchViewIntent("market://details?id=${context.packageName}")
