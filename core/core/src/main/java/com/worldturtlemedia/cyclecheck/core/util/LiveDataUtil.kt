package com.worldturtlemedia.cyclecheck.core.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> liveDataOf(data: T? = null): LiveData<T> = object : LiveData<T>() {
    init {
        value = data
    }
}

fun <T> mutableLiveDataOf(data: T? = null): MutableLiveData<T> =
    MutableLiveData<T>().apply { value = data }
