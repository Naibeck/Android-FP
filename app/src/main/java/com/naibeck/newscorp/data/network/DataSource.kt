package com.naibeck.newscorp.data.network

import arrow.Kind
import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.data.network.mapper.normalizeError
import com.naibeck.newscorp.data.network.mapper.toNetworkError
import com.naibeck.newscorp.runtime.context.Runtime
import retrofit2.Response

fun <F> Runtime<F>.loadImages(): Kind<F, List<PlaceholderImageItem>> = fx.concurrent {
    val response = !effect(context.bgDispatcher) { fetchPlaceholderImages() }
    continueOn(context.mainDispatcher)

    if (response.isSuccessful) {
        response.images()
    } else {
        !raiseError<List<PlaceholderImageItem>>(response.code().toNetworkError())
    }
}.handleErrorWith { error -> raiseError(error.normalizeError()) }

private fun Response<List<PlaceholderImageItem>>.images() = body()!!

private fun <F> Runtime<F>.fetchPlaceholderImages() = context.imageService.fetchImages().execute()
