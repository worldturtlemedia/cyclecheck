package com.worldturtlemedia.cyclecheck.core.network

object HttpCode {

    const val BAD_REQUEST = 400
    const val UNAUTHENTICATED = 401
    const val NOT_FOUND = 404

    const val SERVER_ERROR = 500

    fun isClientError(code: Int) = code in BAD_REQUEST..SERVER_ERROR

    fun isServerError(code: Int) = code in SERVER_ERROR..599
}
