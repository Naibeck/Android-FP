package com.naibeck.newscorp.presentation

import android.widget.ImageView
import arrow.Kind
import com.naibeck.newscorp.business.fetchImages
import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.presentation.views.ImagesView
import com.naibeck.newscorp.runtime.context.Runtime

fun <F> Runtime<F>.loadImages(imagesView: ImagesView): Kind<F, Unit> = fx.concurrent {
    !effect { imagesView.showProgress() }
    val maybeImages = !fetchImages().attempt()
    !effect { imagesView.hideProgress() }

    !effect {
        maybeImages.fold(
            ifLeft = { imagesView.showError() },
            ifRight = { imagesView.show(it) }
        )
    }
}
