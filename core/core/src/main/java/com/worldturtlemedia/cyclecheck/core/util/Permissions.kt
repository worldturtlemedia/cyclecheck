package com.worldturtlemedia.cyclecheck.core.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object Permissions {

    fun hasAllPermissions(context: Context, vararg permission: String): Boolean =
        permission.all { hasPermission(context, it) }

    fun hasPermission(context: Context, permission: String): Boolean =
        getPermissionStatus(context, permission) == PackageManager.PERMISSION_GRANTED

    fun permissionsIsDenied(context: Context, permission: String): Boolean =
        getPermissionStatus(context, permission) == PackageManager.PERMISSION_DENIED

    private fun getPermissionStatus(context: Context, permission: String) =
        ActivityCompat.checkSelfPermission(context, permission)

    const val LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
}

fun Context.hasPermission(permission: String) =
    Permissions.hasPermission(this, permission)

fun Context.hasAllPermissions(vararg permissions: String) =
    Permissions.hasAllPermissions(this, *permissions)

fun Context.permissionIsDenied(permission: String) =
    Permissions.permissionsIsDenied(this, permission)
