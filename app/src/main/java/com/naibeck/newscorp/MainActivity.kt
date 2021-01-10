package com.naibeck.newscorp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import arrow.fx.IO
import arrow.fx.extensions.io.unsafeRun.runNonBlocking
import arrow.unsafe
import com.naibeck.newscorp.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.runtime.application
import com.naibeck.newscorp.runtime.context.runtime
import com.naibeck.newscorp.ui.ImagesView
import com.naibeck.newscorp.ui.loadImages
import timber.log.Timber

class MainActivity : AppCompatActivity(), ImagesView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        unsafe {
            runNonBlocking({
                IO.runtime(application().runtimeContext).loadImages(imagesView = this@MainActivity)
            }, {})
        }
    }

    override fun present(images: List<PlaceholderImageItem>) {
        Timber.d(images.toString())
    }
}
