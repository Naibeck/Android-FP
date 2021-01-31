package com.naibeck.newscorp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naibeck.newscorp.R
import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.databinding.FragmentPlaceholderBinding
import com.naibeck.newscorp.presentation.ImagesAdapter
import com.naibeck.newscorp.presentation.PlaceholderImagesPresenter
import com.naibeck.newscorp.presentation.views.ContainerView
import com.naibeck.newscorp.presentation.views.ImagesView
import com.naibeck.newscorp.runtime.getApp
import com.naibeck.newscorp.ui.MainActivity.Companion.IMAGE_NAME
import com.naibeck.newscorp.ui.MainActivity.Companion.IMAGE_URL

class PlaceholderFragment : Fragment(), ImagesView {

    private var binding: FragmentPlaceholderBinding? = null
    private lateinit var containerView: ContainerView

    private val presenter: PlaceholderImagesPresenter by lazy {
        PlaceholderImagesPresenter(this, context?.getApp()?.runtimeContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_placeholder, container, false)
        setupRefresh()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImages()
    }

    override fun showProgress() = containerView.showProgress()

    override fun hideProgress() = containerView.hideProgress()

    override fun show(images: List<PlaceholderImageItem>) {
        setupImages(
            adapter = ImagesAdapter(
                placeholderImages = images,
                imagesView = this
            )
        )
        binding?.refresh?.isRefreshing = false
    }

    override fun onImageClick(imageView: ImageView?, url: String) {
        imageView?.let {
            val intent = Intent(context, PlaceholderImageDetailActivity::class.java)
            intent.putExtra(IMAGE_URL, url)
            intent.putExtra(IMAGE_NAME, ViewCompat.getTransitionName(imageView))

            startActivity(intent, generateTransitionOption(imageView).toBundle())
        }
    }

    override fun showError() = containerView.showError()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is ContainerView -> containerView = context
        }
    }

    private fun loadImages() = presenter.loadImages()

    private fun setupImages(adapter: ImagesAdapter) {
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding?.images?.layoutManager =
            GridLayoutManager(context, resources.getInteger(R.integer.images_span))
        binding?.images?.adapter = adapter
    }

    private fun setupRefresh() {
        binding?.refresh?.setOnRefreshListener {
            binding?.refresh?.isRefreshing = true
            loadImages()
        }
    }

    private fun generateTransitionOption(imageView: ImageView): ActivityOptionsCompat =
        ActivityOptionsCompat.makeSceneTransitionAnimation(
            containerView as Activity,
            imageView,
            ViewCompat.getTransitionName(imageView) ?: "imageView"
        )

    companion object {
        fun getInstance() = PlaceholderFragment()
    }
}
