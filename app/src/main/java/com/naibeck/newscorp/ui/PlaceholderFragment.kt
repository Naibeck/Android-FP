package com.naibeck.newscorp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import androidx.databinding.DataBindingUtil
import com.naibeck.newscorp.R
import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.databinding.FragmentPlaceholderBinding
import com.naibeck.newscorp.presentation.ContainerView
import com.naibeck.newscorp.presentation.ImagesView
import com.naibeck.newscorp.presentation.PlaceholderImagesPresenter
import com.naibeck.newscorp.runtime.getApp
import timber.log.Timber

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
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadImages()
    }

    override fun showProgress() {
        containerView.showProgress()
    }

    override fun hideProgress() {
        containerView.hideProgress()
    }

    override fun show(images: List<PlaceholderImageItem>) {
        Timber.d(images.toString())
    }

    override fun onImageClick(imageView: ImageView?, url: String) {

    }

    override fun showError() {
        containerView.showError()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is ContainerView -> containerView = context
        }
    }

    companion object {
        fun getInstance() = PlaceholderFragment()
    }
}
