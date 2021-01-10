package com.naibeck.newscorp.network.error

sealed class NetworkError : Throwable() {
    object NotFound : NetworkError()
    object ServerError : NetworkError()
}
