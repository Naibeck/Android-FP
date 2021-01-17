package com.naibeck.newscorp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import arrow.fx.IO
import arrow.fx.extensions.io.unsafeRun.runNonBlocking
import arrow.unsafe
import com.naibeck.newscorp.R
import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.databinding.ActivityMainBinding
import com.naibeck.newscorp.runtime.application
import com.naibeck.newscorp.runtime.context.runtime

class MainActivity : AppCompatActivity(), ImagesView {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

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

    override fun show(images: List<PlaceholderImageItem>) {
        val adapter = ImagesAdapter(placeholderImages = images, imagesView = this)
        setupImages(adapter)
    }

    override fun onImageClick(imageView: ImageView?, url: String) {
        imageView?.let {
            val intent = Intent(this, PlaceholderImageDetailActivity::class.java)
            intent.putExtra("url", url)
            intent.putExtra("name", ViewCompat.getTransitionName(imageView))

            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                imageView,
                ViewCompat.getTransitionName(imageView) ?: "imageView"
            )
            startActivity(intent, bundle.toBundle())
        }
    }

    private fun setupImages(adapter: ImagesAdapter) {
        binding?.images?.layoutManager = GridLayoutManager(this, resources.getInteger(
            R.integer.images_span
        ))
        binding?.images?.adapter = adapter
    }
}
