package com.worldturtlemedia.cyclecheck.core.ktx.util

import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

data class NavigateOptions(
    var args: Bundle? = null,
    var navOptions: NavOptions? = null,
    var extras: Navigator.Extras? = null
)
