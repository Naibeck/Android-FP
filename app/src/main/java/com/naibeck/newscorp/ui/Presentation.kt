package com.naibeck.newscorp.ui

import android.widget.ImageView
import arrow.Kind
import com.naibeck.newscorp.business.fetchImages
import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.runtime.context.Runtime
import timber.log.Timber

interface ImagesView {
    fun showProgress()
    fun hideProgress()
    fun show(images: List<PlaceholderImageItem>)
    fun onImageClick(imageView: ImageView?, url: String)
}

fun <F> Runtime<F>.loadImages(imagesView: ImagesView): Kind<F, Unit> = fx.concurrent {
    !effect { imagesView.showProgress() }
    val maybeImages = !fetchImages().attempt()
    !effect { imagesView.hideProgress() }

    !effect {
        maybeImages.fold(
            ifLeft = { Timber.e(it) },
            ifRight = { imagesView.show(it) }
        )
    }
}
