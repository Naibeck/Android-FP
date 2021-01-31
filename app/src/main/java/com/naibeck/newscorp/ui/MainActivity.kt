package com.naibeck.newscorp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.naibeck.newscorp.R
import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.databinding.ActivityMainBinding
import com.naibeck.newscorp.presentation.ContainerView
import com.naibeck.newscorp.presentation.ImagesAdapter
import com.naibeck.newscorp.presentation.ImagesView
import com.naibeck.newscorp.presentation.PlaceholderImagesPresenter
import com.naibeck.newscorp.presentation.extension.hide
import com.naibeck.newscorp.presentation.extension.show
import com.naibeck.newscorp.runtime.getApp

class MainActivity : AppCompatActivity(), ContainerView {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        loadFragment()
        //loadImages()
        //setupRefresh()
    }

    private fun loadFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PlaceholderFragment.getInstance())
            .commit()
    }

//    override val presenter: PlaceholderImagesPresenter
//        get() = PlaceholderImagesPresenter(view = this, runtime = getApp().runtimeContext)

    override fun showProgress() {
        binding?.progress?.show()
    }

    override fun hideProgress() {
        binding?.progress?.hide()
    }

//    override fun show(images: List<PlaceholderImageItem>) {
//        setupImages(adapter = ImagesAdapter(
//            placeholderImages = images,
//            imagesView = this
//        )
//        )
//        binding?.refresh?.isRefreshing = false
//    }

//    override fun onImageClick(imageView: ImageView?, url: String) {
//        imageView?.let {
//            val intent = Intent(this, PlaceholderImageDetailActivity::class.java)
//            intent.putExtra(IMAGE_URL, url)
//            intent.putExtra(IMAGE_NAME, ViewCompat.getTransitionName(imageView))
//
//            startActivity(intent, generateTransitionOption(imageView).toBundle())
//        }
//    }

    override fun showError() {
        binding?.errorView?.container?.visibility = View.VISIBLE
    }

//    private fun setupImages(adapter: ImagesAdapter) {
//        adapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
//        binding?.images?.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.images_span))
//        binding?.images?.adapter = adapter
//    }

//    private fun setupRefresh() {
//        binding?.refresh?.setOnRefreshListener {
//            binding?.refresh?.isRefreshing = true
//            loadImages()
//        }
//    }
//
//    private fun loadImages() = presenter.loadImages()
//
//    private fun generateTransitionOption(imageView: ImageView): ActivityOptionsCompat =
//        ActivityOptionsCompat.makeSceneTransitionAnimation(
//            this,
//            imageView,
//            ViewCompat.getTransitionName(imageView) ?: "imageView"
//        )
//
    companion object {
        const val IMAGE_URL = "image.url"
        const val IMAGE_NAME = "image.name"
    }
}
