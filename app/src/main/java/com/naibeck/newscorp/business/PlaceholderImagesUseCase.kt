package com.naibeck.newscorp.business

import arrow.Kind
import com.naibeck.newscorp.network.CachePolicy
import com.naibeck.newscorp.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.network.loadImages
import com.naibeck.newscorp.runtime.context.Runtime

fun <F>Runtime<F>.fetchImages(): Kind<F, List<PlaceholderImageItem>> =
    loadImages(policy = CachePolicy.NetworkPolicy)