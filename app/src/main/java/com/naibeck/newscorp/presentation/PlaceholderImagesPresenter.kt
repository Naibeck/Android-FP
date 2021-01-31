package com.naibeck.newscorp.presentation

import arrow.fx.IO
import arrow.fx.extensions.io.unsafeRun.runNonBlocking
import arrow.unsafe
import com.naibeck.newscorp.runtime.context.RuntimeContext
import com.naibeck.newscorp.runtime.context.runtime

class PlaceholderImagesPresenter(
    private val view: ImagesView,
    private val runtime: RuntimeContext?
) {

    fun loadImages() {
        runtime?.let {
            unsafe {
                runNonBlocking({
                    IO.runtime(runtime).loadImages(imagesView = view)
                }, {})
            }
        }
    }
}
