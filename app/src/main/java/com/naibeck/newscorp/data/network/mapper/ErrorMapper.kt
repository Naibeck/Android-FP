package com.naibeck.newscorp.data.network.mapper

import com.naibeck.newscorp.data.network.error.NetworkError

fun Int.toNetworkError() = when (this) {
    404 -> NetworkError.NotFound
    else -> NetworkError.ServerError
}

fun Throwable.normalizeError() = when(this) {
    is NetworkError -> this
    else -> NetworkError.ServerError
}
