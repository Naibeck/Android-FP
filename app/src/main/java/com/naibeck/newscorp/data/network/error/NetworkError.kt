package com.naibeck.newscorp.data.network.error

sealed class NetworkError : Throwable() {
    object NotFound : NetworkError()
    object ServerError : NetworkError()
}
