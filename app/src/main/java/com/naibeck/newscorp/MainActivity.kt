package com.naibeck.newscorp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import arrow.fx.IO
import arrow.fx.extensions.io.unsafeRun.runNonBlocking
import arrow.unsafe
import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.databinding.ActivityMainBinding
import com.naibeck.newscorp.runtime.application
import com.naibeck.newscorp.runtime.context.runtime
import com.naibeck.newscorp.ui.ImagesView
import com.naibeck.newscorp.ui.loadImages

class MainActivity : AppCompatActivity(), ImagesView {
    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        unsafe {
            runNonBlocking({
                IO.runtime(application().runtimeContext).loadImages(imagesView = this@MainActivity)
            }, {})
        }
    }

    override fun showProgress() {
        binding?.progress?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding?.progress?.visibility = View.GONE
    }

    override fun present(images: List<PlaceholderImageItem>) {
        binding?.imagesResponse?.text = images.toString()
    }
}
