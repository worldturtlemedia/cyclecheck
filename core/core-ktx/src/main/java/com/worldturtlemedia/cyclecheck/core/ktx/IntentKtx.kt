package com.worldturtlemedia.cyclecheck.core.ktx

import android.content.Context
import android.content.Intent
import android.net.Uri

fun viewIntent(uri: Uri) = Intent(Intent.ACTION_VIEW, uri)

fun Intent.launch(context: Context) = context.startActivity(this)
