package com.naibeck.newscorp.business

import arrow.Kind
import com.naibeck.newscorp.data.CachePolicy
import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.data.loadImagesWith
import com.naibeck.newscorp.runtime.context.Runtime

fun <F>Runtime<F>.fetchImages(): Kind<F, List<PlaceholderImageItem>> =
    loadImagesWith(policy = CachePolicy.NetworkPolicy)