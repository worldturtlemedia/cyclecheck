package com.worldturtlemedia.cyclecheck.core.view.state

import android.content.Context
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import com.worldturtlemedia.cyclecheck.core.util.Permissions
import com.worldturtlemedia.cyclecheck.core.util.hasPermission
import com.worldturtlemedia.cyclecheck.core.util.permissionIsDenied

sealed class LocationPermissionState {
    object Pending : LocationPermissionState()
    object Granted : LocationPermissionState()
    object Denied : LocationPermissionState()
}

val LocationPermissionState.canUseLocation: Boolean
    get() = this is LocationPermissionState.Granted

val Context.locationPermissionState: LocationPermissionState
    get() = when {
        hasPermission(Permissions.LOCATION) -> LocationPermissionState.Granted
        permissionIsDenied(Permissions.LOCATION) -> LocationPermissionState.Denied
        else -> LocationPermissionState.Pending
    }

fun Context.requestLocationPermission(
    onGranted: () -> Unit,
    onDenied: ((permanent: Boolean) -> Unit) = {}
) {
    runWithPermissions(
        permissions = *arrayOf(Permissions.LOCATION),
        options = QuickPermissionsOptions(
            handleRationale = false,
            permissionsDeniedMethod = { onDenied(false) },
            permanentDeniedMethod = { onDenied(true) }
        ),
        callback = onGranted
    )
}
