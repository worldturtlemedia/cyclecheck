package com.worldturtlemedia.cyclecheck.core.network

interface Mapper<out T> {

    fun map(): T
}
