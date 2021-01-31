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
import com.naibeck.newscorp.presentation.ImagesAdapter
import com.naibeck.newscorp.presentation.ImagesView
import com.naibeck.newscorp.presentation.PlaceholderImagesPresenter
import com.naibeck.newscorp.presentation.loadImages
import com.naibeck.newscorp.runtime.getApp
import com.naibeck.newscorp.runtime.context.runtime
import com.naibeck.newscorp.presentation.extension.hide
import com.naibeck.newscorp.presentation.extension.show

class MainActivity : AppCompatActivity(), ImagesView {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        loadImages()
        setupRefresh()
    }

    override val presenter: PlaceholderImagesPresenter
        get() = PlaceholderImagesPresenter(view = this, runtime = getApp().runtimeContext)

    override fun showProgress() {
        binding?.progress?.show()
    }

    override fun hideProgress() {
        binding?.progress?.hide()
    }

    override fun show(images: List<PlaceholderImageItem>) {
        setupImages(adapter = ImagesAdapter(
            placeholderImages = images,
            imagesView = this
        )
        )
        binding?.refresh?.isRefreshing = false
    }

    override fun onImageClick(imageView: ImageView?, url: String) {
        imageView?.let {
            val intent = Intent(this, PlaceholderImageDetailActivity::class.java)
            intent.putExtra(IMAGE_URL, url)
            intent.putExtra(IMAGE_NAME, ViewCompat.getTransitionName(imageView))

            startActivity(intent, generateTransitionOption(imageView).toBundle())
        }
    }

    override fun showError() {
        binding?.errorView?.container?.visibility = View.VISIBLE
    }

    private fun setupImages(adapter: ImagesAdapter) {
        binding?.images?.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.images_span))
        binding?.images?.adapter = adapter
    }

    private fun setupRefresh() {
        binding?.refresh?.setOnRefreshListener {
            binding?.refresh?.isRefreshing = true
            loadImages()
        }
    }

    private fun loadImages() = presenter.loadImages()

    private fun generateTransitionOption(imageView: ImageView): ActivityOptionsCompat =
        ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            imageView,
            ViewCompat.getTransitionName(imageView) ?: "imageView"
        )

    companion object {
        const val IMAGE_URL = "image.url"
        const val IMAGE_NAME = "image.name"
    }
}
