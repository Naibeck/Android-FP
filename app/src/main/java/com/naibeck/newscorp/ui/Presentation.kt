package com.naibeck.newscorp.ui

import arrow.Kind
import com.naibeck.newscorp.business.fetchImages
import com.naibeck.newscorp.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.runtime.context.Runtime
import timber.log.Timber

interface ImagesView {
    fun present(images: List<PlaceholderImageItem>)
}

fun <F> Runtime<F>.loadImages(imagesView: ImagesView): Kind<F, Unit> = fx.concurrent {
        val maybeImages = !fetchImages().attempt()
        !effect {
            maybeImages.fold(
                ifLeft = { Timber.e(it) },
                ifRight = { imagesView.present(it) }
            )
        }
    }