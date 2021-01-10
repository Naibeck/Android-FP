package com.naibeck.newscorp.network

import arrow.Kind
import com.naibeck.newscorp.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.network.mapper.toNetworkError
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
}.handleErrorWith { error -> raiseError(error) }

private fun Response<List<PlaceholderImageItem>>.images() = body()!!

private fun <F> Runtime<F>.fetchPlaceholderImages() = context.imageService.fetchImages().execute()