package com.naibeck.newscorp.data

import arrow.Kind
import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.data.network.loadImages
import com.naibeck.newscorp.runtime.context.Runtime

sealed class CachePolicy {
    object NetworkPolicy: CachePolicy()
}

fun <F>Runtime<F>.loadImages(policy: CachePolicy): Kind<F, List<PlaceholderImageItem>> = when(policy) {
    CachePolicy.NetworkPolicy -> loadImages()
}
